package com.toystore.User_Management.repository;

// Importing common file handling utility
import com.toystore.common.FileHandler;
// Importing model classes for users
import com.toystore.User_Management.model.AdminUser;
import com.toystore.User_Management.model.RegularUser;
import com.toystore.User_Management.model.User;
// Importing Spring annotation to mark this as a repository
import org.springframework.stereotype.Repository;

// Importing Java collection utilities
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// COMPONENT 01: User Management | Owner: IT26101544
// Annotation telling Spring Boot that this class handles database/file storage operations
@Repository
public class UserRepository {

    // File handler object to read and write user data
    private final FileHandler fileHandler;

    // Constructor to initialize the FileHandler with "users.txt" file
    public UserRepository() {
        this.fileHandler = new FileHandler("users.txt");
    }

    // Saves a single user to the file (synchronized prevents concurrent writing issues)
    public synchronized void save(User user) {
        // Converts user object to text format and appends it as a new line in the file
        fileHandler.appendLine(user.toFileString());
    }

    // Reads all user records from the text file and converts them to Java User objects
    public synchronized List<User> findAll() {
        // Read all raw text lines from "users.txt"
        List<String> lines = fileHandler.readAllLines();
        // Create an empty list to store user objects
        List<User> users = new ArrayList<>();

        // Loop through each line in the file
        for (String line : lines) {
            // Split the line into fields using '|' as the separator
            String[] parts = line.split("\\|");

            // Ensure the line has at least 5 required fields before processing
            if (parts.length >= 5) {
                // Check if the image filename exists (index 5); if not, set it as empty string
                String imageFilename = (parts.length >= 6) ? parts[5] : "";

                // If role is ADMIN, create an AdminUser object
                if ("ADMIN".equals(parts[4])) {
                    users.add(new AdminUser(parts[0], parts[1], parts[2], parts[3], imageFilename));
                }
                // Otherwise, create a RegularUser object
                else {
                    users.add(new RegularUser(parts[0], parts[1], parts[2], parts[3], parts[4], imageFilename));
                }
            }
        }

        // Return the full list of parsed user objects
        return users;
    }

    // Searches for a user matching the given username
    public Optional<User> findByUsername(String username) {
        // Get all users, filter by username, and return the first matching user if found
        return findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    // Searches for a user matching the given user ID
    public Optional<User> findById(String id) {
        // Get all users, filter by ID, and return the first matching user if found
        return findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    // Updates an existing user's information in the text file
    public synchronized void update(User updatedUser) {
        // Fetch all existing users from the file
        List<User> users = findAll();

        // Find the matching user by ID and replace it with updated user data
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                break; // Stop loop once user is found and updated
            }
        }
    }

    // Helper method to overwrite the entire "users.txt" file with the given list of users
    private void saveAll(List<User> users) {
        // Convert all User objects to file text lines
        List<String> lines = users.stream().map(User::toFileString).collect(Collectors.toList());
        // Write all text lines back into the file
        fileHandler.writeAllLines(lines);
    }
}
