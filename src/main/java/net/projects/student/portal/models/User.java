package net.projects.student.portal.models;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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

	@DBRef
	private Set<Role> roles = new HashSet<>();

	public User(String sapId, String email, String password) {
		this.sapId = sapId;
		this.email = email;
		this.password = password;
	}

}
