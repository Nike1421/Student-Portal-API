package net.projects.student.portal.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.projects.student.portal.models.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	// MAYBE CHANGE TO FINDBYSAPID
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
