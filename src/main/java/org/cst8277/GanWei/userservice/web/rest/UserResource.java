package org.cst8277.GanWei.userservice.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.cst8277.GanWei.userservice.domain.User;
import org.cst8277.GanWei.userservice.domain.UserRole;
import org.cst8277.GanWei.userservice.repository.UserRepository;
import org.cst8277.GanWei.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;

    // private final UserRepository userRepository;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        LOG.debug("REST request to get all Users ");

        return userService.getAllUsers();
    }

    // GET: Retrieve a user by ID
    @GetMapping("/{username}")
    public Optional<User> getUserById(@PathVariable String username) {
        LOG.debug("REST request to get User by id: {}", username);
        return userService.getUserById(username);
    }

    @GetMapping("/producers")
    public List<User> getProducers() {
        LOG.debug("REST request to get all Users by roles");
        return userService.getUsersByRoles(UserRole.PRODUCER);
    }

    @GetMapping("/subscribers")
    public List<User> getSubscribers() {
        LOG.debug("REST request to get all Users by roles");
        return userService.getUsersByRoles(UserRole.SUBSCRIBER);
    }

    @GetMapping("/subscribers/{producerId}")
    public List<User> getSubscribersForProducer(@RequestParam String producerId) {
        return userService.getSubscribersForProducer(producerId);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        LOG.debug("REST request to register a new user: {}", user);

        // Validate user input (can be extended for more detailed validation)
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request if username is missing
        }

        // Check if the user already exists
        Optional<User> existingUser = userService.getUserById(user.getUsername());
        if (existingUser.isPresent()) {
            LOG.warn("Attempt to register a user with an existing username: {}", user.getUsername());
            return ResponseEntity.status(409).body(null); // 409 Conflict
        }

        // Save the new user
        User savedUser = userService.saveUser(user);

        // Build response with created URI
        URI location;
        try {
            location = new URI("/api/users/" + savedUser.getUsername());
        } catch (URISyntaxException e) {
            LOG.error("Error creating URI for registered user", e);
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.created(location).body(savedUser);
    }
}
