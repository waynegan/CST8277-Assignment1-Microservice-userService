package com.epicwuxia.userservice.web.rest;

import com.epicwuxia.userservice.domain.User;
import com.epicwuxia.userservice.domain.UserRole;
import com.epicwuxia.userservice.repository.UserRepository;
import com.epicwuxia.userservice.service.UserService;
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
}
