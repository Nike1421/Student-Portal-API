package net.projects.student.portal.models;

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
	private long SAPID;
	private String studentName;
	private String contactNumber;
}
