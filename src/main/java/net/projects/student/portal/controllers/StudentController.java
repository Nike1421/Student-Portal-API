package net.projects.student.portal.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.projects.student.portal.models.faculty.Faculty;
import net.projects.student.portal.models.student.Student;
import net.projects.student.portal.payloads.response.MessageResponse;
import net.projects.student.portal.repository.StudentRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@PostMapping("/addStudent")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('MODERATOR')")
	public String saveStudent(@RequestBody Student student) {
		studentRepository.save(student);
		System.out.println(student.getSapID());
		return "Student Saved with id:";
	}

	@GetMapping("/getAllStudents")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('MODERATOR') or hasRole('FACULTY')")
	public List<?> getStudents() {
		return studentRepository.findAll();
	}
	
	@PutMapping("/updateStudent")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> updateFaculty(@RequestBody Student student) {
		studentRepository.save(student);
		return ResponseEntity.ok(new MessageResponse("Student Updated!"));
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
