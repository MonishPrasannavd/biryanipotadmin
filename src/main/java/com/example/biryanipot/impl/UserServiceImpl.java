package com.example.biryanipot.impl;

import com.example.biryanipot.dto.RegisterRequest;
import com.example.biryanipot.entity.User;
import com.example.biryanipot.repository.UserRepository;
import com.example.biryanipot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Attempting to load user by username: {}", username); // Debug log

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found for username: {}", username); // Error log
                    return new UsernameNotFoundException("User not found");
                });

        logger.info("User found: {}", user.getUsername()); // Info log
        logger.debug("User details from DB: {}", user); // Debug log

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // No roles/authorities for now
        );
    }

    @Override
    public User findByUsername(String username) {
        logger.debug("Searching for user with username: {}", username); // Debug log

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found for username: {}", username); // Error log
                    return new UsernameNotFoundException("User not found");
                });

        logger.info("User found: {}", user.getUsername());
        return user;
    }

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        logger.info("Registering new user with username: {}", registerRequest.getUsername()); // Info log

        // Check if the username already exists
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            logger.warn("Username already exists: {}", registerRequest.getUsername()); // Warn log
            throw new RuntimeException("Username already exists");
        }

        // Create new user object
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());

        // Encode the password and save it
        logger.debug("Encoding password for user: {}", registerRequest.getUsername()); // Debug log
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Save the new user to the database
        User savedUser = userRepository.save(newUser);
        logger.info("User registered successfully with username: {}", savedUser.getUsername()); // Info log

        return savedUser;
    }
}
