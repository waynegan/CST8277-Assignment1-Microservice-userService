package org.cst8277.GanWei.userservice.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.cst8277.GanWei.userservice.domain.User;
import org.cst8277.GanWei.userservice.domain.UserRole;
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
