package net.projects.student.portal.models.faculty;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import net.projects.student.portal.models.Member;

@Getter
@Setter
@Document(collection = "Faculty")
public class Faculty extends Member {
	@Id
	private String sapID;
	private List<Publications> publicationList;
	private List<Subject> subjectList;
}