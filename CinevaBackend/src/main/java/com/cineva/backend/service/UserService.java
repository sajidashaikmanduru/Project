package com.cineva.backend.service;

import com.cineva.backend.entity.User;
import com.cineva.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("UserService initialized");
    }

    public User signIn(String emailOrPhone, String password) throws Exception {
        logger.debug("Attempting sign-in for emailOrPhone: {}", emailOrPhone);
        
        Optional<User> userOptional = userRepository.findByEmailOrPhone(emailOrPhone);
        if (userOptional.isEmpty()) {
            logger.error("User not found with email or phone: {}", emailOrPhone);
            throw new Exception("User not found with email or phone: " + emailOrPhone);
        }

        User user = userOptional.get();
        logger.debug("Found user: {}", user.getEmailOrPhone());
        
        if (!user.getPassword().equals(password)) {
            logger.error("Invalid password for user: {}", emailOrPhone);
            throw new Exception("Invalid password");
        }

        logger.info("Sign-in successful for user: {}", emailOrPhone);
        return user;
    }
}