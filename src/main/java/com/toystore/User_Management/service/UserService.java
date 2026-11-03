package com.toystore.User_Management.service;

import com.toystore.common.IdGenerator;
import com.toystore.User_Management.model.RegularUser;
import com.toystore.User_Management.model.User;
import com.toystore.User_Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

// COMPONENT 01:User Management | Owner:IT26101544

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
    public void registerRegularUser(String username,String password,String email,String membershipType) throws Exception {

        // Step 1:Check if the given username is already taken in the system
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("Username already exists");
        }

        // Step 2:Generate a custom unique user ID starting with the prefix "U"
        String id =IdGenerator.generateId("U");

        // Step 3:Hash the plain-text password using SHA-256 for secure storage
        String hashedPassword =hashPassword(password);

        // Step 4:Create a new RegularUser object with all provided user details
        RegularUser user =new RegularUser(id,username,hashedPassword,email,membershipType);

        // Step 5:Save the new user record into the database via the repository
        userRepository.save(user);
    }

    /**
     * Authenticates a user during login:
     * 1. Finds the user by their username.
     * 2. Hashes the incoming password input.
     * 3. Compares the generated hash with the stored hash.
     * 4. Returns the User object if valid, or throws an exception if invalid.
     */
    public User login(String username,String password) throws Exception {

        // Search for the user by username in the repository
        Optional<User> userOpt =userRepository.findByUsername(username);

        // Check if the user exists in the database
        if (userOpt.isPresent()) {
            User user =userOpt.get();

            // Hash the input password and compare it with the hashed password stored in the DB
            if (user.getPasswordHash().equals(hashPassword(password))) {
                return user; // Authentication successful:return the authenticated user
            }
        }

        // Throw exception if the username does not exist or the password does not match
        throw new Exception("Invalid username or password");
    }

    /**
     * Retrieves all registered users from the repository.
     * @return List of all users in the system.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Finds a single user by their unique ID.
     * @param id User ID to search for.
     * @return User object if found, or null if no user matches the ID.
     */
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Directly updates user details in the repository layer.
     * @param user User object containing updated fields.
     */
    public void updateUser(User user) {
        userRepository.update(user);
    }

    /**
     * Updates profile info (email,membership type) and optional profile image:
     * 1. Fetch existing user by ID.
     * 2. Update basic fields (email).
     * 3. Update membership type if the user is an instance of RegularUser.
     * 4. Replace old profile picture with a new one if a file is uploaded.
     * 5. Persist updated user state to the repository.
     */
    public void updateUserProfile(String userId,String email,String membershipType,org.springframework.web.multipart.MultipartFile imageFile) throws Exception {

        // Fetch the user by unique ID
        User user =getUserById(userId);

        // Proceed only if the user exists in the database
        if (user != null) {

            // Update the email address
            user.setEmail(email);

            // Type checking and downcasting: Update membership type only if user is a RegularUser
            if (user instanceof RegularUser && membershipType != null) {
                ((RegularUser) user).setMembershipType(membershipType);
            }

            // Check if a valid,non-empty image file was uploaded
            if (imageFile != null && !imageFile.isEmpty()) {

                // Remove the existing image from local storage if one exists
                if (user.getImageFilename() != null && !user.getImageFilename().isEmpty()) {
                    com.toystore.common.ImageStorageUtil.deleteImage("users",user.getImageFilename());
                }

                // Save the new image file to the storage and get its new filename
                String newFilename =com.toystore.common.ImageStorageUtil.saveImage("users",user.getId(),imageFile);

                // Attach the new image filename to the user profile
                user.setImageFilename(newFilename);
            }
            // Save all updated user information back to the repository
            updateUser(user);
        }
    }

    /**
     * Deletes a user record and cleans up associated file assets:
     * 1. Check if the user exists.
     * 2. Delete the user's profile image file if present.
     * 3. Delete the user record from the database.
     */
    public void deleteUser(String id) {

        // Fetch the user object by ID
        User user = getUserById(id);

        // Proceed only if the target user is found
        if (user != null) {

            // If the user has a profile picture saved,delete the file from storage
            if (user.getImageFilename() != null && !user.getImageFilename().isEmpty()) {
                com.toystore.common.ImageStorageUtil.deleteImage("users",user.getImageFilename());
            }

            // Remove the user entry from the database
            userRepository.delete(id);
        }
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
            MessageDigest digest =MessageDigest.getInstance("SHA-256");

            // Hash the password string into a byte array
            byte[] hash =digest.digest(password.getBytes());

            // Encode byte array to a Base64 readable string and return
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            // Wrap checked algorithm exception into a RuntimeException
            throw new RuntimeException("Error hashing password", e);
        }
    }

}