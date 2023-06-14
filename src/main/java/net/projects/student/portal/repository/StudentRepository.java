package net.projects.student.portal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.projects.student.portal.models.Student;

public interface StudentRepository extends MongoRepository<Student, Long>{

}
