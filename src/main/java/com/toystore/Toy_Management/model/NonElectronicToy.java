// Declares the package containing toy model classes.
package com.toystore.Toy_Management.model;

/** Toy subtype for toys that do not require electrical power. */
/** Implements toy behavior and safety information for non-electronic products. */
// Defines a non-electronic toy that inherits common fields and behavior from Toy.
public class NonElectronicToy extends Toy {

    /** Creates a non-electronic toy without an uploaded image. */
    // Initializes the toy with its details and the fixed "Non-Electronic" type.
    public NonElectronicToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity) {
        // Passes the supplied details and type to the parent Toy constructor.
        super(id, name, brand, "Non-Electronic", ageGroup, price, stockQuantity);
    }

    /** Creates a non-electronic toy with an optional stored image filename. */
    // Initializes the toy with its details and an image filename.
    public NonElectronicToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity, String imageFilename) {
        // Passes all details, including the image filename, to the parent constructor.
        super(id, name, brand, "Non-Electronic", ageGroup, price, stockQuantity, imageFilename);
    }

    /** Describes the toy's screen-free style of play. */
    // Overrides the parent method with a description for non-electronic toys.
    @Override
    public String displayDetails() {
        // Builds the display text using this toy's name and brand.
        return "Non-Electronic Toy: " + getName() + " by " + getBrand() + ". Classic screen-free tactile play.";
    }

    /** Provides the safety guidance shown for this toy type. */
    // Implements the safety information method defined by the parent class.
    @Override
    public String getSafetyInfo() {
        // Returns the safety guidance for hands-on creative play.
        return "Non-toxic materials. Safe for hands-on, active creative play.";
    }

    /** Returns the file-format type token used to restore this subtype. */
    // Implements the method that identifies this toy subtype.
    @Override
    public String getToyType() {
        // Returns the type token used to identify non-electronic toys.
        return "NON_ELECTRONIC";
    }
}
