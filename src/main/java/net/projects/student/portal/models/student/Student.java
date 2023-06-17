package net.projects.student.portal.models.student;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.projects.student.portal.models.Member;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Document(collection = "Student")
public class Student extends Member {
	@Id
	private String sapID;
	private String studentName;
	private String studentEmail;
	private String studentContactNumber;
	private String studentJoiningYear;
	private String studentGraduationYear;

	private String resumeLink;
	private String linkedinLink;
	private String portfolioLink;
	// Initialize with size 8
	private List<Float> studentCGPAList;
	private List<Internship> studentInternshipList;
	private List<Project> studentProjectList;
}
