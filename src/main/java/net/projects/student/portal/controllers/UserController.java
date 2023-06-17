package net.projects.student.portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.projects.student.portal.models.User;
import net.projects.student.portal.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	
	@PostMapping("/addUser")
	public String addNewUser(@RequestBody User user) {
		userRepository.save(user);
		return "User successfully added";
	}
}
