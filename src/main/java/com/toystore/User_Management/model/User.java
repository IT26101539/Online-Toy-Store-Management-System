package com.toystore.User_Management.model;

// COMPONENT 01: User Management | Owner: IT26101544
// Abstract base class representing a general User in the application
public abstract class User {

    // Private attributes to protect sensitive user data (Encapsulation)
    private String id;             // Unique identifier for the user
    private String username;       // User's login name
    private String passwordHash;   // Hashed version of the password for security
    private String email;          // User's email address
    private String imageFilename;  // Profile picture file name

    // Overloaded constructor for users without a profile picture
    public User(String id, String username, String passwordHash, String email) {
        // Calls the main constructor passing an empty string for the image
        this(id, username, passwordHash, email, "");
    }

    // Main constructor to initialize all user fields
    public User(String id, String username, String passwordHash, String email, String imageFilename) {
        this.id = id;                                                     // Set user ID
        this.username = username;                                         // Set username
        this.passwordHash = passwordHash;                                 // Set password hash
        this.email = email;                                               // Set email
        this.imageFilename = imageFilename != null ? imageFilename : ""; // Set image filename (prevents null values)
    }
    // Returns the user ID
    public String getId() {
        return id;
    }

    // Updates the user ID
    public void setId(String id) {
        this.id = id;
    }

    // Returns the username
    public String getUsername() {
        return username;
    }

    // Updates the username
    public void setUsername(String username) {
        this.username = username;
    }