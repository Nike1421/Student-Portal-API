package net.projects.student.portal.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class User<T> {

	@Id
	private String id;

	@NotBlank
	@Size(min = 10, max = 10)
	private String sapID;

	@NotBlank
	@Size(max = 50)
	private String email;
	@NotBlank
	@JsonIgnore
	@Size(min = 8, max = 50)
	private String password;
	
	@NotBlank
	private boolean isSuperAdmin;

	
	@DBRef
	private Set<Role> roles = new HashSet<>();
	
	@DBRef
	private T userMember;

	public User(String sapID, String email, String password, boolean isSuperAdmin) {
		this.sapID = sapID;
		this.email = email;
		this.password = password;
		this.isSuperAdmin = isSuperAdmin;
	}
}
