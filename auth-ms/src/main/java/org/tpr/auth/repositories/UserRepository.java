package org.tpr.auth.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tpr.auth.models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
