package net.projects.student.portal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {
	
	private String name;
	private String email;
	private String contactNumber;
	private String joiningYear;

	private String resumeLink;
	private String linkedinLink;
	private String portfolioLink;
}
