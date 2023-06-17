package net.projects.student.portal.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.projects.student.portal.models.ERole;
import net.projects.student.portal.models.Role;

public interface RoleRepository extends MongoRepository<Role, String>{
	Optional<Role> findByName(ERole name);
}
