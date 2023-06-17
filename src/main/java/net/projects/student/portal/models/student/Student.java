package net.projects.student.portal.models.student;

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

@Document(collection = "Student")
public class Student extends Member {
	@Id
	private String sapID;
	private String graduationYear;
	// Initialize with size 8
	private List<Float> studentCGPAList;
	private List<Internship> studentInternshipList;
	private List<Project> studentProjectList;
}
