package net.projects.student.portal.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
 
	@GetMapping("/user")
	@PreAuthorize("hasRole('STUDENT') or hasRole('MODERATOR') or hasRole('SUPERADMIN') or hasRole('FACULTY')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}
	
	@GetMapping("/faculty")
	@PreAuthorize("hasRole('FACULTY')")
	public String facultyAccess() {
		return "Faculty Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
