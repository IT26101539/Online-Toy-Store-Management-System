package com.toystore.User_Management.model;

// COMPONENT 01: User Management | Owner: IT26101544

// RegularUser class extending parent class User (Inheritance concept)
public class RegularUser extends User {

    // Private variable for encapsulation (Stores user type: REGULAR or PREMIUM)
    private String membershipType;

    // Constructor to initialize basic user details and membership type
    public RegularUser(String id, String username, String passwordHash, String email, String membershipType) {
        super(id, username, passwordHash, email); // Calls parent User class constructor
        this.membershipType = membershipType;   // Assigns membership type to instance variable
    }


}