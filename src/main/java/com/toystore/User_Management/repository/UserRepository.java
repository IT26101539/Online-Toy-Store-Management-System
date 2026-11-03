package com.toystore.User_Management.repository;

// Importing common file handling utility
import com.toystore.common.FileHandler;
import com.toystore.User_Management.model.User;
// Importing Spring annotation to mark this as a repository
import org.springframework.stereotype.Repository;

// Importing Java collection utilities
import java.util.ArrayList;
import java.util.List;

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
                // Parsing logic goes here
            }
        }

        // Return the full list of parsed user objects
        return users;
    }
}
