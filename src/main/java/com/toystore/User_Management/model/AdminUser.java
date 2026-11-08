package com.toystore.User_Management.model;

/**
 * COMPONENT 01:User Management Subsystem
 * Module Owner:IT26101544
 * Description:Model class representing an Administrator user in the Online Toy Store system.
 */
public class AdminUser extends User {

    /**
     * Parameterized Constructor:
     * Initializes a new AdminUser instance using basic credentials by invoking
     * the superclass (User) constructor.
     *
     * @param id           Unique identifier for the user
     * @param username     System username for login
     * @param passwordHash Secure hashed password string
     * @param email        Contact email address of the administrator
     */
    public AdminUser(String id,String username,String passwordHash,String email) {
        // Explicitly call the 4-argument constructor of the parent 'User' class
        super(id,username,passwordHash,email);
    }

    /**
     * Overloaded Parameterized Constructor:
     * Initializes a new AdminUser instance including a custom profile image
     * by invoking the corresponding superclass constructor.
     *
     * @param id            Unique identifier for the user
     * @param username      System username for login
     * @param passwordHash  Secure hashed password string
     * @param email         Contact email address of the administrator
     * @param imageFilename Filename or path of the uploaded profile picture
     */
    public AdminUser(String id,String username,String passwordHash,String email,String imageFilename) {
        // Explicitly call the 5-argument constructor of the parent 'User' class
        super(id,username,passwordHash,email,imageFilename);
    }

    /**
     * Polymorphic Role Retrieval Method:
     * Overrides the abstract or base getRole() method from the parent User class
     * to specifically identify this user type as an administrator.
     *
     * @return String constant "ADMIN" representing the user's role privilege level
     */
    @Override
    public String getRole() {
        // Return the fixed administrative privilege tag
        return "ADMIN";
    }
}