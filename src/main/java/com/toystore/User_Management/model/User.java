package com.toystore.User_Management.model;

/**
 * COMPONENT 01:User Management Subsystem
 * Module Owner:IT26101544
 * Description:Abstract base model class representing a generic user in the Online Toy Store system.
 * Implements core Object-Oriented Principles: Abstraction,Encapsulation and Code Reusability.
 */
public abstract class User {

    /**
     * Encapsulated Private Fields:
     * Stores fundamental user account credentials and profile metadata.
     * Private modifier ensures data hiding and prevents direct unauthorized access from outside classes.
     */
    private String id;             // Unique identifier for the user account
    private String username;       // System login username handle
    private String passwordHash;   // Secure cryptographically hashed password string
    private String email;          // Primary contact and communication email address
    private String imageFilename;  // Stored filename of the user's uploaded profile picture

    /**
     * Overloaded Constructor (Basic Details):
     * Initializes a User instance with core credentials when no custom profile picture is provided.
     * Automatically chains to the primary constructor using default empty string parameters.
     *
     * @param id           Unique identifier for the user
     * @param username     System login username
     * @param passwordHash Secure hashed password string
     * @param email        Contact email address
     */
    public User(String id,String username,String passwordHash,String email) {
        // Chain constructor call,passing an empty string as a default fallback for imageFilename
        this(id,username,passwordHash,email,"");
    }

    /**
     * Primary Parameterized Constructor:
     * Initializes all core user attributes including profile image metadata.
     * Includes null-safety checks to prevent null pointer exceptions for image filenames.
     *
     * @param id             Unique identifier for the user
     * @param username       System login username
     * @param passwordHash   Secure hashed password string
     * @param email          Contact email address
     * @param imageFilename  Filename of the profile picture (null-safe handling applied)
     */
    public User(String id,String username,String passwordHash,String email,String imageFilename) {
        this.id =id;                                                     // Assign unique ID
        this.username =username;                                         // Assign login username
        this.passwordHash =passwordHash;                                 // Assign password hash
        this.email =email;                                               // Assign email address
        // Conditional assignment:if input image filename is null,default to an empty string
        this.imageFilename =imageFilename != null ? imageFilename : "";
    }

    /**
     * Getter Method:Retrieves the unique user identification string.
     * @return String representing the user ID
     */
    public String getId() {
        return id;
    }

    /**
     * Setter Method:Updates or modifies the unique user identification string.
     * @param id New unique user ID to set
     */
    public void setId(String id) {
        this.id =id;
    }

    /**
     * Getter Method:Retrieves the system login username.
     * @return String representing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter Method:Updates or modifies the system login username.
     * @param username New username to set
     */
    public void setUsername(String username) {
        this.username =username;
    }

    /**
     * Getter Method:Retrieves the encrypted password hash.
     * @return String representing the secure password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Setter Method:Updates or modifies the encrypted password hash.
     * @param passwordHash New secure password hash string to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash =passwordHash;
    }

    /**
     * Getter Method:Retrieves the user's primary email address.
     * @return String representing the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter Method:Updates or modifies the user's primary email address.
     * @param email New email address to set
     */
    public void setEmail(String email) {
        this.email =email;
    }

    /**
     * Getter Method:Retrieves the filename of the user's profile picture.
     * @return String representing the image filename
     */
    public String getImageFilename() {
        return imageFilename;
    }

    /**
     * Setter Method: Updates the profile image filename with built-in null safety.
     * @param imageFilename New image filename to set (null inputs convert to empty string)
     */
    public void setImageFilename(String imageFilename) {
        this.imageFilename =imageFilename != null ? imageFilename : "";
    }

    /**
     * Dynamic Web Resource URL Generator:
     * Evaluates whether a valid profile picture filename exists.If available,constructs
     * the full server path for uploads;otherwise,falls back to a default SVG placeholder graphic.
     *
     * @return String containing the web-accessible image path or default placeholder URL
     */
    public String getImageUrl() {
        // Validate if image filename is non-null and contains non-whitespace characters
        if (imageFilename != null && !imageFilename.trim().isEmpty()) {
            return "/uploads/users/" + imageFilename; // Resolves to uploaded user asset folder
        }
        return "/images/placeholder-user.svg"; // Fallback vector placeholder graphic
    }

    /**
     * Abstract Polymorphic Method:
     * Declares a contract that must be fulfilled by all concrete subclasses (e.g., AdminUser,RegularUser)
     * to define their respective user role privileges.
     *
     * @return String representing the specific role classification of the user
     */
    public abstract String getRole();

    /**
     * Flat-File Persistence Formatter:
     * Serializes user object attributes into a single pipe-delimited text string format
     * suitable for text file storage or legacy database record exports.
     *
     * @return Formatted string structured as: ID | Username | PasswordHash | Email | Role | ImageFilename
     */
    public String toFileString() {
        // Format object data separated by pipe symbols (|) for structured text storage
        return id + "|" + username + "|" + passwordHash + "|" + email + "|" + getRole() + "|" + (imageFilename != null ? imageFilename : "");
    }
}