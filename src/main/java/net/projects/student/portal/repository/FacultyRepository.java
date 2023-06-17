package net.projects.student.portal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.projects.student.portal.models.faculty.Faculty;

public interface FacultyRepository extends MongoRepository<Faculty, String>{
	Faculty findBySapID(String sapID);
}
