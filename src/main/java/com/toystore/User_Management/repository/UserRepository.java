package com.toystore.User_Management.repository;

// Importing common file handling utility
import com.toystore.common.FileHandler;
import com.toystore.User_Management.model.User;
// Importing Spring annotation to mark this as a repository
import org.springframework.stereotype.Repository;

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
}
