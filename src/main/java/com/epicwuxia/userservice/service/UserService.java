package com.epicwuxia.userservice.service;

import com.epicwuxia.userservice.client.SubscriptionClient;
import com.epicwuxia.userservice.domain.Subscription;
import com.epicwuxia.userservice.domain.User;
import com.epicwuxia.userservice.domain.UserRole;
import com.epicwuxia.userservice.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findByUsername(id);
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
