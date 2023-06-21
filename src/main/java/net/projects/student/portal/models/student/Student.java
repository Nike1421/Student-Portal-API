package net.projects.student.portal.models.student;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Document(collection = "Student")
public class Student {
	@Id
	private String sapID;
	
	@NotBlank
	@Size(max = 20)
	private String studentName;
	
	@NotBlank
	@Email
	private String studentEmail;
	
	@NotBlank
	@Size(min = 10, max = 10)
	private String studentContactNumber;
	
	@NotBlank
	@Size(min = 4, max = 4)
	private String studentJoiningYear;
	
	@NotBlank
	@Size(min = 4, max = 4)
	private String studentGraduationYear;

	@NotBlank
	private String resumeLink;
	
	@NotBlank
	private String linkedinLink;
	
	@NotBlank
	private String portfolioLink;

	private List<Float> studentCGPAList;
	private List<Internship> studentInternshipList;
	private List<Project> studentProjectList;
}
