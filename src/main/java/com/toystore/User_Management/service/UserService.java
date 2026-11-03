package com.toystore.User_Management.service;

import com.toystore.common.IdGenerator;
import com.toystore.User_Management.model.RegularUser;
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
     * Handles registration for regular users:
     * 1. Validates whether the username already exists.
     * 2. Generates a custom unique ID.
     * 3. Hashes the raw password for security.
     * 4. Creates a new RegularUser instance and saves it to the database.
     */
    public void registerRegularUser(String username, String password, String email, String membershipType) throws Exception {

        // Step 1: Check if the given username is already taken in the system
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("Username already exists");
        }

        // Step 2: Generate a custom unique user ID starting with the prefix "U"
        String id = IdGenerator.generateId("U");

        // Step 3: Hash the plain-text password using SHA-256 for secure storage
        String hashedPassword = hashPassword(password);

        // Step 4: Create a new RegularUser object with all provided user details
        RegularUser user = new RegularUser(id, username, hashedPassword, email, membershipType);

        // Step 5: Save the new user record into the database via the repository
        userRepository.save(user);
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