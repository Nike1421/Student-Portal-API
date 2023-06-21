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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import net.projects.student.portal.models.User;
import net.projects.student.portal.models.faculty.Faculty;
import net.projects.student.portal.models.student.Student;
import net.projects.student.portal.repository.FacultyRepository;
import net.projects.student.portal.repository.RoleRepository;
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
	RoleRepository roleRepository;

	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("/view")
	public ResponseEntity<?> getSelfDetails(HttpServletRequest request) {
		String jwtString = jwtUtils.getJwtFromCookies(request);
		try {
			User<?> user = null;
			if (jwtString != null && jwtUtils.validateJwtToken(jwtString)) {
				String usernameString = jwtUtils.getUserNameFromJwtToken(jwtString);

				user = userRepository.findBySapID(usernameString).orElseThrow(
						() -> new UsernameNotFoundException("User Not Found with SAP ID " + usernameString));
			}
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateSelfDetails(HttpServletRequest request, @RequestBody JsonNode jsonNode) {
		String jwtString = jwtUtils.getJwtFromCookies(request);
		final ObjectMapper mapper = new ObjectMapper();
		try {
			if (jwtString != null && jwtUtils.validateJwtToken(jwtString)) {
				String usernameString = jwtUtils.getUserNameFromJwtToken(jwtString);

				if (facultyRepository.existsBySapID(usernameString)) {
					Faculty retrievedFaculty = mapper.convertValue(jsonNode.get("userMember"), Faculty.class);
					facultyRepository.save(retrievedFaculty);
				} else if (studentRepository.existsBySapID(usernameString)) {
					Student retrievedStudent = mapper.convertValue(jsonNode.get("userMember"), Student.class);
					studentRepository.save(retrievedStudent);
				}
				userRepository.save(mapper.convertValue(jsonNode, User.class));
			}
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(e);
		}
	}

//	FIX THIS TOMORROW

	public Faculty convertJsonToFaculty(JsonNode facultyJsonNode) {
//		List<Publications> facultyPublicationList = new ArrayList<>();
//		// Add null check
//		ArrayNode arrayNode = (ArrayNode) facultyJsonNode.get("publicationList");
//		if (arrayNode.isArray()) {
//			for (JsonNode jsonNode : arrayNode) {
//				System.out.println(jsonNode);
//			}
//		}
		Faculty retrievedFaculty = new Faculty(facultyJsonNode.get("sapID").asText(),
				facultyJsonNode.get("facultyName").asText(), facultyJsonNode.get("facultyEmail").asText(),
				facultyJsonNode.get("facultyContactNumber").asText(),
				facultyJsonNode.get("facultyJoiningYear").asText(), facultyJsonNode.get("resumeLink").asText(),
				facultyJsonNode.get("linkedinLink").asText(), facultyJsonNode.get("portfolioLink").asText(), null,
				null);

		return retrievedFaculty;

	}

	public Student convertJsonToStudent(JsonNode studentJsonNode) {
		return null;
	}
}
