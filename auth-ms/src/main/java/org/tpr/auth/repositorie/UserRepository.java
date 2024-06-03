package org.tpr.auth.repositorie;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tpr.auth.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
