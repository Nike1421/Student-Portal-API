package net.projects.student.portal.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.projects.student.portal.models.Student;
import net.projects.student.portal.repository.StudentRepository;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@PostMapping("/addStudent")
	public String saveStudent(@RequestBody Student student) {
		studentRepository.save(student);
		return "Student Saved with id:";
	}

	@GetMapping("/getAllStudents")
	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	@GetMapping("/getStudent/{id}")
	public Optional<Student> getStudent(@PathVariable long id) {
		return studentRepository.findById(id);
	}

	@DeleteMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable long id) {
		studentRepository.deleteById(id);
		return "Deleted";
	}
}
