package net.projects.student.portal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.projects.student.portal.models.student.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
	// MAYBE CHANGE TO FIND BY SAPID
	Student findBySapID(String sapID);

	Boolean existsBySapID(String sapID);
}
