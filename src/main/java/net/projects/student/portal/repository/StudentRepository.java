package net.projects.student.portal.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.projects.student.portal.models.student.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
	// MAYBE CHANGE TO FIND BY SAPID
	Optional<Student> findBySapID(String sapID);
}
