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

/**
 * COMPONENT 01: User Management Subsystem
 * Module Owner: IT26101544
 * Description: Repository data-access component responsible for managing user data persistence
 * using a flat text file ("users.txt"). Implements thread-safe CRUD operations.
 */
@Repository
public class UserRepository {

    /**
     * File Handler Dependency:
     * Manages low-level text file reading,writing and line-appending operations for "users.txt".
     */
    private final FileHandler fileHandler;

    /**
     * Default Constructor:
     * Initializes the repository component and links it to the text-based persistent store "users.txt".
     */
    public UserRepository() {
        this.fileHandler = new FileHandler("users.txt");
    }

    /**
     * Save Operation (Thread-Safe):
     * Appends a single user's serialized text record to the storage file.
     * Uses the 'synchronized' keyword to prevent race conditions during concurrent write requests.
     *
     * @param user The User object (AdminUser or RegularUser) to persist
     */
    public synchronized void save(User user) {
        // Convert user object into pipe-delimited text format and append as a new line in the file
        fileHandler.appendLine(user.toFileString());
    }

    /**
     * Retrieval Operation (Thread-Safe):
     * Reads all raw text lines from "users.txt",parses each pipe-delimited entry,
     * and reconstructs them back into appropriate concrete Java User objects.
     *
     * @return List containing all parsed User objects found in the storage file
     */
    public synchronized List<User> findAll() {
        // Read all raw text lines from the storage file
        List<String> lines = fileHandler.readAllLines();
        // Create an empty dynamic list to hold instantiated user objects
        List<User> users = new ArrayList<>();

        // Iterate through each persistent text line
        for (String line : lines) {
            // Split line records into individual attributes using the pipe delimiter ("|")
            String[] parts = line.split("\\|");

            // Validate that the record contains at least the minimum required fields (ID,username,password,email,role)
            if (parts.length >= 5) {
                // Safely extract optional image filename at index 5 if present; otherwise default to an empty string
                String imageFilename = (parts.length >= 6) ? parts[5] : "";

                // Polymorphic instantiation check: if role identifier is "ADMIN", instantiate an AdminUser
                if ("ADMIN".equals(parts[4])) {
                    users.add(new AdminUser(parts[0],parts[1],parts[2],parts[3],imageFilename));
                }
                // Otherwise, instantiate a RegularUser passing membership classification and profile picture
                else {
                    users.add(new RegularUser(parts[0],parts[1],parts[2],parts[3],parts[4],imageFilename));
                }
            }
        }

        // Return the complete collection of parsed user records
        return users;
    }

    /**
     * Query Operation:
     * Searches the user repository for an account matching the specified username string.
     *
     * @param username The system username to search for
     * @return Optional containing the matched User object if found or empty otherwise
     */
    public Optional<User> findByUsername(String username) {
        // Stream all users,filter by matching username property and return the first match
        return findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    /**
     * Query Operation:
     * Searches the user repository for an account matching the unique user ID string.
     *
     * @param id The unique user ID to search for
     * @return Optional containing the matched User object if found, or empty otherwise
     */
    public Optional<User> findById(String id) {
        // Stream all users,filter by matching ID property and return the first match
        return findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    /**
     * Update Operation (Thread-Safe):
     * Modifies an existing user's stored information by locating their record via ID,
     * replacing it with the updated object and rewriting the persistent file.
     *
     * @param updatedUser The User object containing modified attributes
     */
    public synchronized void update(User updatedUser) {
        // Fetch all current user records from storage
        List<User> users = findAll();

        // Iterate through the list to locate the record matching the updated user's ID
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                // Replace the old object reference with the updated user instance
                users.set(i, updatedUser);
                break; // Exit loop immediately once the record is updated
            }
        }
        // Persist the modified list back to the storage file
        saveAll(users);
    }

    /**
     * Delete Operation (Thread-Safe):
     * Removes a user record from the persistent text file based on their unique user ID.
     *
     * @param id The unique identifier of the user to delete
     */
    public synchronized void delete(String id) {
        // Filter out the user whose ID matches the target, keeping all other records
        List<User> users = findAll().stream()
                .filter(u -> !u.getId().equals(id))
                .collect(Collectors.toList());

        // Overwrite storage file with the filtered user collection
        saveAll(users);
    }

    /**
     * Helper Persistence Method (Private):
     * Overwrites the entire "users.txt" file with a fresh list of serialized user string lines.
     *
     * @param users The complete list of User objects to write to disk
     */
    private void saveAll(List<User> users) {
        // Map each User object into its corresponding pipe-delimited file string format
        List<String> lines = users.stream().map(User::toFileString).collect(Collectors.toList());
        // Write the complete line collection back to the physical file
        fileHandler.writeAllLines(lines);
    }
}