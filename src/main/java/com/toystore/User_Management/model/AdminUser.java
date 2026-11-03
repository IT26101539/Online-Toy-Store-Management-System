package com.toystore.User_Management.model;

// COMPONENT 01: User Management | Owner: IT26101544

// AdminUser class inherits properties and methods from the parent User class
public class AdminUser extends User {

    // Main constructor to create an AdminUser with basic details
    public AdminUser(String id,String username,String passwordHash,String email) {
        super(id,username,passwordHash,email); // Calls the base User class constructor
    }
    // Overloaded constructor to create an AdminUser with a profile image
    public AdminUser(String id,String username,String passwordHash,String email,String imageFilename) {
        super(id,username,passwordHash,email,imageFilename); // Calls base constructor with image parameter
    }
    // Annotation indicating this method overrides a method in the parent class
    @Override
    public String getRole() { // Polymorphic method to get the user's role
        return "ADMIN"; // Returns fixed string identifying this user as an Admin
    }

}