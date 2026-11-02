package com.toystore.User_Management.model;

// COMPONENT 01: User Management | Owner: IT26101544
// Abstract base class representing a general User in the system
public abstract class User {

    // Private fields for data encapsulation (protecting user details)
    private String id;             // Unique user identification number
    private String username;       // User's login username
    private String passwordHash;   // Secure hashed password string
    private String email;          // User's contact email address
    private String imageFilename;  // Profile picture filename
}