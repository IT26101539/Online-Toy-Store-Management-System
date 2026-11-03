package com.toystore.User_Management.model;

/**
 * COMPONENT 01: User Management Subsystem
 * Module Owner: IT26101544
 * Description: Model class representing a Regular customer/user in the Online Toy Store.
 * Demonstrates Object-Oriented Concepts: Inheritance,Encapsulationnand Polymorphism.
 */
public class RegularUser extends User {

    /**
     * Instance Field:
     * Encapsulated private field storing the specific membership classification
     * for regular store customers (e.g.,"REGULAR","PREMIUM").
     */
    private String membershipType;

    /**
     * Parameterized Constructor:
     * Initializes a standard RegularUser instance with basic account credentials
     * and assigns their account membership tier.
     *
     * @param id             Unique identifier for the user account
     * @param username       System login handle/username
     * @param passwordHash   Encrypted password hash string
     * @param email          Primary email address of the customer
     * @param membershipType Customer tier tier status (e.g.,REGULAR,PREMIUM)
     */
    public RegularUser(String id,String username,String passwordHash,String email,String membershipType) {
        // Delegate parent attribute initialization to the superclass (User) constructor
        super(id,username,passwordHash,email);
        // Initialize instance specific attribute
        this.membershipType = membershipType;
    }

    /**
     * Overloaded Parameterized Constructor:
     * Initializes a RegularUser instance including custom profile picture metadata
     * along with membership classification details.
     *
     * @param id             Unique identifier for the user account
     * @param username       System login handle/username
     * @param passwordHash   Encrypted password hash string
     * @param email          Primary email address of the customer
     * @param membershipType Customer tier status (e.g.,REGULAR,PREMIUM)
     * @param imageFilename  Path or file identifier for the avatar image
     */
    public RegularUser(String id,String username,String passwordHash,String email,String membershipType,String imageFilename) {
        // Delegate parent attributes including profile image to superclass constructor
        super(id,username,passwordHash,email,imageFilename);
        // Initialize instance specific attribute
        this.membershipType = membershipType;
    }

    /**
     * Getter Method:
     * Encapsulation accessor method to retrieve the current membership status of the user.
     *
     * @return String representing the membership tier (e.g.,"REGULAR","PREMIUM")
     */
    public String getMembershipType() {
        return membershipType;
    }

    /**
     * Setter Method:
     * Encapsulation mutator method to update or upgrade the customer's membership tier.
     *
     * @param membershipType New membership tier string to be assigned
     */
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    /**
     * Polymorphic Role Retrieval Method:
     * Overrides the base getRole() method inherited from the parent User class.
     * Dynamically returns the assigned membership status to dictate user privilege levels.
     *
     * @return String matching the membershipType property (e.g.,"REGULAR","PREMIUM")
     */
    @Override
    public String getRole() {
        // Dynamic dynamic role resolution based on membership level
        return membershipType;
    }
}