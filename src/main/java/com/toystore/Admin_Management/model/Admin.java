// This file belongs to the Admin_Management.model package (folder)
package com.toystore.Admin_Management.model;

// Import the User class (written by another component) so Admin can extend it
import com.toystore.User_Management.model.User;

// COMPONENT 04: Admin Management
// Owner: It26101539
// Encapsulation and Inheritance
// Admin "extends" User, so it gets id, username, passwordHash and email from User
// and adds its own extra field: permissions

public class Admin extends User{

    // Encapsulation: private field, accessed only via getter/setter
    private String permissions; // exsample - "ALL", "READ_ONLY"

    // Constructor - creates a new Admin object
    public Admin(String id,String username,String passwordHash,String email,String permissions) {
        // super(...) calls the parent (User) constructor to set the inherited fields
        super(id,username,passwordHash,email);
        // Set the field that belongs only to Admin
        this.permissions = permissions;
    }

    // Getter - read the permissions value
    public String getPermissions() {
        return permissions;
    }

    // Setter - change the permissions value
    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    // Polymorphism - overrides getRole() from User so an Admin always returns "ADMIN"
    @Override
    public String getRole() {
        return "ADMIN";
    }

    // Polymorphism - overrides toFileString() from User.
    // Builds one line of text to save in admins.txt, separated by "|"
    // Format: id|username|passwordHash|email|ADMIN|permissions

    @Override
    public String toFileString() {
        return getId()+"|"+getUsername()+"|"+getPasswordHash()+"|"+getEmail()+"|"+getRole()+"|"+permissions;
    }
}