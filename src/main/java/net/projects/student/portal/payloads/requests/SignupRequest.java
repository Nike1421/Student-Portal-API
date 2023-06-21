package net.projects.student.portal.payloads.requests;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
	@NotBlank
	@Size(min = 3, max = 20)
	private String sapID;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	private Set<String> roles;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	private boolean isSuperAdmin;

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}
}
