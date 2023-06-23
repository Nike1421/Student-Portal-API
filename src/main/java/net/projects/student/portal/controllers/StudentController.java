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
import net.projects.student.portal.models.student.Student;
import net.projects.student.portal.payloads.response.MessageResponse;
import net.projects.student.portal.repository.RoleRepository;
import net.projects.student.portal.repository.StudentRepository;
import net.projects.student.portal.repository.UserRepository;

/**
 * REST API Controller for handling CRUD methods for Student. Main API Mapping is
 * "/api/student" 
 * 
 * @author Om Naik (@Nike1421)
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@PostMapping("/addStudent")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<?> saveStudent(@Valid @RequestBody Student student) {
		try {
			// Add Document to Collection
			studentRepository.save(student);

			logger.info("Added New Student with SAP ID: " + student.getSapID());

			return ResponseEntity.ok(new MessageResponse("New Student Added!"));
		} catch (Exception e) {
			logger.error("Cannot add new student: {}", e);
			return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
		}
	}

	@PutMapping("/setStudentAsModerator")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> setStudentAsModerator(@Valid @RequestBody String sapId) {

		try {
			@SuppressWarnings("unchecked")
			User<Student> user = userRepository.findBySapID(sapId)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with SAP ID " + sapId));

			Set<Role> roles = user.getRoles();

			Role studentRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(studentRole);

			user.setRoles(roles);

			userRepository.save(user);

			logger.info("Student with SAP ID " + sapId + " has been given the role MODERATOR.");

			return ResponseEntity.ok(new MessageResponse("Student is now Moderator."));
		} catch (Exception e) {
			logger.error("Error Occurred: {}", e);

			return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
		}
	}

	@GetMapping("/getAllStudents")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('MODERATOR') or hasRole('FACULTY')")
	public List<?> getStudents() {
		return studentRepository.findAll();
	}

	@PutMapping("/updateStudent")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateFaculty(@RequestBody Student student) {
		try {
			studentRepository.save(student);
			logger.info("Student with SAP ID " + student.getSapID() + " updated.");
			return ResponseEntity.ok(new MessageResponse("Student Updated!"));
		} catch (Exception e) {
			logger.error("Student Details cannot be updated: {}", e);
			return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
		}
	}

	@GetMapping("/getStudent/{id}")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('MODERATOR') or hasRole('FACULTY')")
	public Optional<?> getStudent(@PathVariable String id) {
		return studentRepository.findById(id);
	}

	@DeleteMapping("/deleteStudent/{id}")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('MODERATOR')")
	public String deleteStudent(@PathVariable String id) {
		studentRepository.deleteById(id);
		return "Deleted";
	}
}
