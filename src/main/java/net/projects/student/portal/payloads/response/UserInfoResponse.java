package net.projects.student.portal.payloads.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
	private String id;
	private String sapID;
	private String email;
	private List<String> roles;
}
