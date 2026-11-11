// Declares the package containing toy model classes.
package com.toystore.Toy_Management.model;

/** Toy subtype for soft, stuffed products. */
/** Implements toy behavior and safety information for plush products. */
// Defines a plush toy that inherits common fields and behavior from Toy.
public class PlushToy extends Toy {

    /** Creates a plush toy without an uploaded image. */
    // Initializes the toy with its details and the fixed "Plush" type.
    public PlushToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity) {
        // Passes the supplied details and type to the parent Toy constructor.
        super(id, name, brand, "Plush", ageGroup, price, stockQuantity);
    }

    /** Creates a plush toy with an optional stored image filename. */
    // Initializes the toy with its details and an image filename.
    public PlushToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity, String imageFilename) {
        // Passes all details, including the image filename, to the parent constructor.
        super(id, name, brand, "Plush", ageGroup, price, stockQuantity, imageFilename);
    }

    /** Describes the soft toy for display in the store. */
    // Overrides the parent method with a description for plush toys.
    @Override
    public String displayDetails() {
        // Builds the display text using this toy's name and brand.
        return "Plush Toy: " + getName() + " by " + getBrand() + ". Soft and cuddly.";
    }

    /** Provides the care and safety guidance shown for this toy type. */
    // Implements the safety information method defined by the parent class.
    @Override
    public String getSafetyInfo() {
        // Returns care and safety guidance for plush toys.
        return "Safety: Keep away from fire. Remove all tags before giving to a child.";
    }

    /** Returns the file-format type token used to restore this subtype. */
    // Implements the method that identifies this toy subtype.
    @Override
    public String getToyType() {
        // Returns the type token used to identify plush toys.
        return "PLUSH";
    }
}
