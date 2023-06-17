package net.projects.student.portal.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.projects.student.portal.models.faculty.Faculty;
import net.projects.student.portal.repository.FacultyRepository;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	FacultyRepository facultyRepository;
	
	@PostMapping("/addFaculty")
	public String addFaculty(@RequestBody Faculty faculty) {
		facultyRepository.save(faculty);
		return "Faculty Saved";
	}
	
	@GetMapping("/getAllFaculties")
	public List<?> getFaculties() {
		return facultyRepository.findAll();
	}

	@GetMapping("/getFaculty/{id}")
	public Optional<?> getFaculty(@PathVariable String id) {
		return facultyRepository.findById(id);
	}

	@DeleteMapping("/deleteFaculty/{id}")
	public String deleteFaculty(@PathVariable String id) {
		facultyRepository.deleteById(id);
		return "Deleted";
	}
	
}
