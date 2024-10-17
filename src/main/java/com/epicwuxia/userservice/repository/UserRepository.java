package com.epicwuxia.userservice.repository;

import com.epicwuxia.userservice.domain.User;
import com.epicwuxia.userservice.domain.UserRole;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String Id);

    List<User> findAllByUsernameIn(List<String> username);

    List<User> findByRoles(UserRole roles);
    // List<User> findByProducer();
}
