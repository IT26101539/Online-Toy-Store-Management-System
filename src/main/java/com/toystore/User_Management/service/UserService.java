package com.toystore.User_Management.service;

import com.toystore.User_Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

// COMPONENT 01:User Management | Owner: IT26101544

// Mark this class as a Spring Service component to manage user-related business logic
@Service
public class UserService {

    // Repository dependency to interact with the database/storage layer
    private final UserRepository userRepository;

    // Use constructor-based dependency injection to bring in the UserRepository
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository =userRepository;
    }

    /**
     * Private helper method to securely convert raw text passwords into SHA-256 hash format.
     * Encodes the resulting byte array as a Base64 string.
     *
     * @param password Plain-text raw password string.
     * @return Base64 encoded SHA-256 password hash.
     */
    private String hashPassword(String password) {
        try {
            // Initialize SHA-256 digest algorithm instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hash the password string into a byte array
            byte[] hash = digest.digest(password.getBytes());

            // Encode byte array to a Base64 readable string and return
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            // Wrap checked algorithm exception into a RuntimeException
            throw new RuntimeException("Error hashing password", e);
        }
    }

}