package net.projects.student.portal.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * REST API Controller for handling User Authentication. Main API Mapping is
 * "/api/auth" Contains two API routes: "/signup" & "/signin" for handling User
 * Registration and User Login respectively.
 * 
 * @author Om Naik (@Nike1421)
 *
 */
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
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {

			// Authenticate using passed Auth Token
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getSapID(), loginRequest.getPassword()));

			// Set the Authentication Principal
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Get the set Authentication Principal
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			// Generate JWT Token and create a Cookie
			ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

			// Get all available roles
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			logger.info("Authenticated User with SAP ID: " + loginRequest.getSapID());
			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new UserInfoResponse(
					userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
		} catch (Exception e) {
			logger.error("Cannot Authenticate User: {} ", e);
			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		// Check if User is already registered
		if (userRepository.existsBySapID(signUpRequest.getSapID())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User already registered!"));
		}

		// Check if Email is already in use
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		try {
			// Set to store all roles
			Set<Role> roles = new HashSet<>();

			// Check for adding SUPERADMIN role
			if (signUpRequest.isSuperAdmin()) {
				Role adminRole = roleRepository.findByRoleName(ERole.ROLE_SUPERADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);
			}

			// If user's SAP ID is in Faculty Collection, add the FACULTY role
			if (facultyRepository.existsBySapID(signUpRequest.getSapID())) {

				User<Faculty> user = new User<Faculty>(signUpRequest.getSapID(), signUpRequest.getEmail(),
						passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.isSuperAdmin());

				Role facultyRole = roleRepository.findByRoleName(ERole.ROLE_FACULTY)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(facultyRole);

				user.setUserMember(facultyRepository.findBySapID(signUpRequest.getSapID()));

				user.setRoles(roles);

				userRepository.save(user);
				
				logger.info("Faculty with SAP ID " + signUpRequest.getSapID() + " registered successfully.");

				return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
			}
			// If user's SAP ID is in Student Collection, add the STUDENT role
			else if (studentRepository.existsBySapID(signUpRequest.getSapID())) {

				User<Student> user = new User<Student>(signUpRequest.getSapID(), signUpRequest.getEmail(),
						passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.isSuperAdmin());

				Role studentRole = roleRepository.findByRoleName(ERole.ROLE_STUDENT)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(studentRole);

				user.setUserMember(studentRepository.findBySapID(signUpRequest.getSapID()));

				user.setRoles(roles);

				userRepository.save(user);
				
				logger.info("Student with SAP ID " + signUpRequest.getSapID() + " registered successfully.");

				return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
			}
			// If user's SAP ID is not found in either Student or Faculty Collection, throw
			// Error
			else {
				logger.error("Error: No Student or Faculty Exists With SAP ID:  " + signUpRequest.getSapID());
				
				return ResponseEntity.badRequest()
						.body(new MessageResponse("Error: No Student or Faculty Exists With SAP ID: "
								+ signUpRequest.getSapID() + ". Please Contact Administrator."));
			}
		} catch (Exception e) {
			logger.error("Error while registering user: ", e);
			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
		}
	}
}
