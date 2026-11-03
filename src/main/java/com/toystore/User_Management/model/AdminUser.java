package com.toystore.User_Management.model;

// COMPONENT 01: User Management | Owner: IT26101544

// AdminUser class inherits properties and methods from the parent User class
public class AdminUser extends User {

    // Main constructor to create an AdminUser with basic details
    public AdminUser(String id, String username, String passwordHash, String email) {
        super(id, username, passwordHash, email); // Calls the base User class constructor
    }

}