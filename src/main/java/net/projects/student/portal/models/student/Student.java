package net.projects.student.portal.models;

import java.lang.invoke.CallSite;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Document(collection = "Student")
public class Student {
	
	@Id
	private long sapID;
	private String studentName;
	private String studentEmail;
	private String studentContactNumber;
	private String studentJoiningYear;
	private String studentGraduationYear;
	
	private String resumeLink;
	private String linkedinLink;
	
//	private float cgpaSemester1;
//	private float cgpaSemester2;
//	private float cgpaSemester3;
//	private float cgpaSemester4;
//	private float cgpaSemester5;
//	private float cgpaSemester6;
//	private float cgpaSemester7;
//	private float cgpaSemester8;
	
	// Initialize with size 8
	private List<Float> studentCGPAList;
	private List<Internship> studentInternshipList;
	private List<Project> studentProjectList;
	
	
}
