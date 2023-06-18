package net.projects.student.portal.models.faculty;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.projects.student.portal.models.Member;

@Getter
@Setter
@ToString

@Document(collection = "Faculty")
public class Faculty extends Member {
	@Id
	private String sapID;
	
	private String facultyName;
	private String facultyEmail;
	private String facultyContactNumber;
	private String facultyJoiningYear;
	
	private String resumeLink;
	private String linkedinLink;
	private String portfolioLink;
	private List<Publications> publicationList;
	private List<Subject> subjectList;
}
