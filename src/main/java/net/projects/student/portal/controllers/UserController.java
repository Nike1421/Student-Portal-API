package net.projects.student.portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import net.projects.student.portal.models.ERole;
import net.projects.student.portal.models.Member;
import net.projects.student.portal.models.User;
import net.projects.student.portal.payloads.response.UserAllDataResponse;
import net.projects.student.portal.repository.FacultyRepository;
import net.projects.student.portal.repository.StudentRepository;
import net.projects.student.portal.repository.UserRepository;
import net.projects.student.portal.security_config.jwt_services.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	FacultyRepository facultyRepository;

	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("/view")
	public ResponseEntity<?> getSelfDetails(HttpServletRequest request) {
		String jwtString = jwtUtils.getJwtFromCookies(request);
		Member member = null;
		try {
			User user = null;
			if (jwtString != null && jwtUtils.validateJwtToken(jwtString)) {
				String usernameString = jwtUtils.getUserNameFromJwtToken(jwtString);

				user = userRepository.findBySapId(usernameString).orElseThrow(
						() -> new UsernameNotFoundException("User Not Found with SAP ID " + usernameString));

				if (user.getRoles().stream().filter(x -> x.getRoleName().equals(ERole.ROLE_FACULTY)) != null) {

					member = facultyRepository.findBySapID(usernameString);
				} else if (user.getRoles().stream().filter(x -> x.getRoleName().equals(ERole.ROLE_STUDENT)) != null) {

					member = studentRepository.findBySapID(usernameString);
				}
			}
			return ResponseEntity.ok().body(new UserAllDataResponse(user, member));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateSelfDetails(HttpServletRequest request, @RequestBody Member member) {
		String jwtString = jwtUtils.getJwtFromCookies(request);

		try {
			User user = null;
			if (jwtString != null && jwtUtils.validateJwtToken(jwtString)) {
				String usernameString = jwtUtils.getUserNameFromJwtToken(jwtString);

				user = userRepository.findBySapId(usernameString).orElseThrow(
						() -> new UsernameNotFoundException("User Not Found with SAP ID " + usernameString));
			}
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().build();
		}
	}
}
