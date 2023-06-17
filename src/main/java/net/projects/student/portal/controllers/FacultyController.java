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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.projects.student.portal.models.faculty.Faculty;
import net.projects.student.portal.payloads.response.MessageResponse;
import net.projects.student.portal.repository.FacultyRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	FacultyRepository facultyRepository;
	
	@PostMapping("/addFaculty")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<?> addFaculty(@RequestBody Faculty faculty) {
		facultyRepository.save(faculty);
		return ResponseEntity.ok(new MessageResponse("New Faculty Added!"));
	}
	
	@GetMapping("/getAllFaculties")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('MODERATOR')")
	public List<?> getFaculties() {
		return facultyRepository.findAll();
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
