package com.toystore.User_Management.model;

// COMPONENT 01: User Management | Owner: IT26101544

// RegularUser class extending parent class User (Inheritance concept)
public class RegularUser extends User {

    // Private variable for encapsulation (Stores user type: REGULAR or PREMIUM)
    private String membershipType;

    // Constructor to initialize basic user details and membership type
    public RegularUser(String id,String username,String passwordHash,String email,String membershipType) {
        super(id,username,passwordHash,email); // Calls parent User class constructor
        this.membershipType = membershipType;   // Assigns membership type to instance variable
    }
    // Overloaded constructor that also includes profile image filename
    public RegularUser(String id,String username,String passwordHash,String email,String membershipType,String imageFilename) {
        super(id,username,passwordHash,email,imageFilename); // Calls parent constructor with image parameter
        this.membershipType = membershipType;                  // Assigns membership type
    }
    // Getter method to retrieve the membership type
    public String getMembershipType() {
        return membershipType; // Returns membershipType
    }

    // Setter method to update the membership type
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType; // Sets new value for membershipType
    }
    // Overrides the getRole method from User class (Polymorphism concept)
    @Override
    public String getRole() {
        return membershipType; // Returns specific membership type as the user role (e.g., REGULAR, PREMIUM)
    }
}