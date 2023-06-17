package net.projects.student.portal.models.student;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Internship {
	private String companyName;
	private String roleName;
	private String description;
	
	private String startDate;
	private String endDate;
	private String stipend;
	private int hoursPerDay;
	private boolean isFullTime;
	
	private String internshipCertificateLink;
	
}
