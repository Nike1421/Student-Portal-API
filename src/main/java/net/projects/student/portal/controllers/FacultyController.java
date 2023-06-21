package net.projects.student.portal.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.projects.student.portal.models.ERole;
import net.projects.student.portal.models.Role;
import net.projects.student.portal.models.User;
import net.projects.student.portal.models.faculty.Faculty;
import net.projects.student.portal.payloads.response.MessageResponse;
import net.projects.student.portal.repository.FacultyRepository;
import net.projects.student.portal.repository.RoleRepository;
import net.projects.student.portal.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faculty")
public class FacultyController {
	// TODO: UPDATE FACULTY CONTROLLER

	@Autowired
	FacultyRepository facultyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@PostMapping("/addFaculty")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> addFaculty(@RequestBody Faculty faculty) {
		facultyRepository.save(faculty);
		
		return ResponseEntity.ok(new MessageResponse("New Faculty Added!"));
	}

	@PutMapping("/setFacultyAsModerator")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> setFacultyAsModerator(@RequestBody String sapId) {

		User user = userRepository.findBySapID(sapId)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with SAP ID " + sapId));

		Set<Role> roles = user.getRoles();

		Role facultyRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(facultyRole);

		user.setRoles(roles);

		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("Faculty is now Moderator."));
	}

	@GetMapping("/getAllFaculties")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('MODERATOR')")
	public List<?> getFaculties() {
		return facultyRepository.findAll();
	}

	@PutMapping("/updateFaculty")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateFaculty(@RequestBody Faculty faculty) {
		facultyRepository.save(faculty);
		return ResponseEntity.ok(new MessageResponse("Faculty Updated!"));
	}

	@GetMapping("/getFaculty/{id}")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('MODERATOR')")
	public Optional<?> getFaculty(@PathVariable String id) {

		return facultyRepository.findById(id);
	}

	@DeleteMapping("/deleteFaculty/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public String deleteFaculty(@PathVariable String id) {
		facultyRepository.deleteById(id);
		return "Deleted";
	}

}
