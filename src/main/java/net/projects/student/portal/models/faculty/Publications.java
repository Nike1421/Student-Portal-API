package net.projects.student.portal.models.faculty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Publications {
	private String title;
	private String conferenceName;
	private String doi;
	private String year;
}
