package net.projects.student.portal.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import jakarta.validation.Valid;
import net.projects.student.portal.models.ERole;
import net.projects.student.portal.models.Role;
import net.projects.student.portal.models.User;
import net.projects.student.portal.models.faculty.Faculty;
import net.projects.student.portal.payloads.response.MessageResponse;
import net.projects.student.portal.repository.FacultyRepository;
import net.projects.student.portal.repository.RoleRepository;
import net.projects.student.portal.repository.UserRepository;

/**
 * REST API Controller for handling CRUD methods for Faculty. Main API Mapping is
 * "/api/faculty" 
 * 
 * @author Om Naik (@Nike1421)
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faculty")
public class FacultyController {
	@Autowired
	FacultyRepository facultyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	private static final Logger logger = LoggerFactory.getLogger(FacultyController.class);

	@PostMapping("/addFaculty")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> addFaculty(@Valid @RequestBody Faculty faculty) {
		try {
			// Add Document to Collection
			facultyRepository.save(faculty);

			logger.info("Added New Faculty with SAP ID: " + faculty.getSapID());

			return ResponseEntity.ok(new MessageResponse("New Faculty Added!"));
		} catch (Exception e) {
			logger.error("Cannot add new faculty: {}", e);
			return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
		}
	}

	@PutMapping("/setFacultyAsModerator")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> setFacultyAsModerator(@Valid @RequestBody String sapId) {

		try {
			@SuppressWarnings("unchecked")
			User<Faculty> user = userRepository.findBySapID(sapId)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with SAP ID " + sapId));

			Set<Role> roles = user.getRoles();

			Role facultyRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(facultyRole);

			user.setRoles(roles);

			userRepository.save(user);

			logger.info("Faculty with SAP ID " + sapId + " has been given the role MODERATOR.");

			return ResponseEntity.ok(new MessageResponse("Faculty is now Moderator."));
		} catch (Exception e) {
			logger.error("Error Occurred: {}", e);

			return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
		}
	}

	@GetMapping("/getAllFaculties")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('MODERATOR')")
	public List<?> getFaculties() {
		return facultyRepository.findAll();
	}

	@PutMapping("/updateFaculty")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateFaculty(@RequestBody Faculty faculty) {
		try {
			facultyRepository.save(faculty);
			logger.info("Faculty with SAP ID " + faculty.getSapID() + " updated.");
			return ResponseEntity.ok(new MessageResponse("Faculty Updated!"));
		} catch (Exception e) {
			logger.error("Faculty Details cannot be updated: {}", e);
			return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
		}
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
