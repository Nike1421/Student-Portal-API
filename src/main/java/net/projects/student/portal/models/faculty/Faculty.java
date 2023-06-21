package net.projects.student.portal.models.faculty;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor

@Document(collection = "Faculty")
public class Faculty {
	@Id
	private String sapID;
	
	@NotBlank
	@Size(max = 20)
	private String facultyName;
	
	@NotBlank
	@Email
	private String facultyEmail;
	
	@NotBlank
	@Size(min = 10, max = 10)
	private String facultyContactNumber;
	
	@NotBlank
	@Size(min = 4, max = 4)
	private String facultyJoiningYear;
	
	@NotBlank
	private String resumeLink;
	
	@NotBlank
	private String linkedinLink;
	
	private String portfolioLink;
	private List<Publications> publicationList;
	private List<Subject> subjectList;
}
