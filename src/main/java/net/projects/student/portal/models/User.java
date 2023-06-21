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


@NoArgsConstructor
@ToString
@Document(collection = "User")
public class User {

	@Id
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	@NotBlank
	@Size(min = 10, max = 10)
	private String sapID;

	@Getter
	@Setter
	@NotBlank
	@Size(max = 50)
	private String email;

	@Getter
	@Setter
	@NotBlank
	@JsonIgnore
	@Size(min = 8, max = 50)
	private String password;
	
	@Getter
	@Setter
	@NotBlank
	private boolean isSuperAdmin;

	
	@DBRef
	private Set<Role> roles = new HashSet<>();
	
	@Getter
	@Setter
	@DBRef
	private Member userMember;

	public User(String sapID, String email, String password, boolean isSuperAdmin) {
		this.sapID = sapID;
		this.email = email;
		this.password = password;
		this.isSuperAdmin = isSuperAdmin;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
