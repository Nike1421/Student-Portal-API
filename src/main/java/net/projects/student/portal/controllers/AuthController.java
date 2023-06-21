package net.projects.student.portal.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import net.projects.student.portal.models.ERole;
import net.projects.student.portal.models.Role;
import net.projects.student.portal.models.User;
import net.projects.student.portal.models.faculty.Faculty;
import net.projects.student.portal.models.student.Student;
import net.projects.student.portal.payloads.requests.LoginRequest;
import net.projects.student.portal.payloads.requests.SignupRequest;
import net.projects.student.portal.payloads.response.MessageResponse;
import net.projects.student.portal.payloads.response.UserInfoResponse;
import net.projects.student.portal.repository.FacultyRepository;
import net.projects.student.portal.repository.RoleRepository;
import net.projects.student.portal.repository.StudentRepository;
import net.projects.student.portal.repository.UserRepository;
import net.projects.student.portal.security_config.jwt_services.JwtUtils;
import net.projects.student.portal.security_config.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	FacultyRepository facultyRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getSapID(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(
				new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsBySapID(signUpRequest.getSapID())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User already registered!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		Set<Role> roles = new HashSet<>();

		if (signUpRequest.isSuperAdmin()) {
			Role adminRole = roleRepository.findByRoleName(ERole.ROLE_SUPERADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(adminRole);
		}

		if (facultyRepository.existsBySapID(signUpRequest.getSapID())) {

			User<Faculty> user = new User<Faculty>(signUpRequest.getSapID(), signUpRequest.getEmail(),
					passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.isSuperAdmin());

			Role facultyRole = roleRepository.findByRoleName(ERole.ROLE_FACULTY)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(facultyRole);

			user.setUserMember(facultyRepository.findBySapID(signUpRequest.getSapID()));

			user.setRoles(roles);

			userRepository.save(user);
		} else if (studentRepository.existsBySapID(signUpRequest.getSapID())) {

			User<Student> user = new User<Student>(signUpRequest.getSapID(), signUpRequest.getEmail(),
					passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.isSuperAdmin());

			Role studentRole = roleRepository.findByRoleName(ERole.ROLE_STUDENT)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(studentRole);
			user.setUserMember(studentRepository.findBySapID(signUpRequest.getSapID()));

			user.setRoles(roles);

			userRepository.save(user);
		}

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
