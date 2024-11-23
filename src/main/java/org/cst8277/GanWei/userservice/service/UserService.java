package org.cst8277.GanWei.userservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.cst8277.GanWei.userservice.client.SubscriptionClient;
import org.cst8277.GanWei.userservice.domain.Subscription;
import org.cst8277.GanWei.userservice.domain.User;
import org.cst8277.GanWei.userservice.domain.UserRole;
import org.cst8277.GanWei.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@org.springframework.stereotype.Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final SubscriptionClient subscriptionClient;

    public UserService(UserRepository userRepository, SubscriptionClient subscriptionClient) {
        this.userRepository = userRepository;
        this.subscriptionClient = subscriptionClient;
    }

    public List<User> getAllUsers() {
        LOG.info("Retrieved {} users from the database", userRepository.findAll().size());

        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findByUsername(id);
    }

    public User saveUser(User user) {
        LOG.debug("Saving new user: {}", user);

        return userRepository.save(user);
    }

    public List<User> getSubscribersForProducer(String producerId) {
        List<Subscription> subscriptions = subscriptionClient.getSubscribersByProducerId(producerId);
        List<String> subscriberIds = subscriptions.stream().map(Subscription::getSubscriberId).collect(Collectors.toList());
        java.util.logging.Logger.getLogger("UserService").info("Subscriber IDs: " + subscriberIds);

        return userRepository.findAllByUsernameIn(subscriberIds);
    }

    public List<User> getUsersByRoles(UserRole roles) {
        if (roles.equals(UserRole.PRODUCER)) {
            return Stream.concat(
                userRepository.findByRoles(UserRole.PRODUCER).stream(),
                userRepository.findByRoles(UserRole.BOTH).stream()
            ).collect(Collectors.toList());
        }

        if (roles.equals(UserRole.SUBSCRIBER)) {
            return Stream.concat(
                userRepository.findByRoles(UserRole.SUBSCRIBER).stream(),
                userRepository.findByRoles(UserRole.BOTH).stream()
            ).collect(Collectors.toList());
        }

        if (roles.equals(UserRole.BOTH)) {
            return userRepository.findAll(); // Use findAll() provided by MongoRepository
        }

        return null;
    }
}
