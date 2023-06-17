package net.projects.student.portal.models.student;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Project {
	private String projectTitle;
	private String projectDescription;
	private String projectGitHubLink;
}
