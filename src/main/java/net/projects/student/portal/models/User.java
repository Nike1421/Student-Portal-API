package net.projects.student.portal.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "User")
public class User {

	@Id
	private String id;

	@NotBlank
	@Size(min = 10, max = 10)
	private String sapId;

	@NotBlank
	@Size(max = 50)
	private String email;

	@NotBlank
	@Size(min = 8, max = 50)
	private String password;
	
	@NotBlank
	private boolean isSuperAdmin;

	@DBRef
	private Set<Role> roles = new HashSet<>();
	
	@DBRef
	private Member userMember;

	public User(String sapId, String email, String password, boolean isSuperAdmin) {
		this.sapId = sapId;
		this.email = email;
		this.password = password;
		this.isSuperAdmin = isSuperAdmin;
	}

}
