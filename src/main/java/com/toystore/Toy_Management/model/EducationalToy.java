// Declares the package containing toy model classes.
package com.toystore.Toy_Management.model;

/** Toy subtype for products intended to support learning and development. */
/** Implements toy behavior and safety information for educational products. */
// Defines an educational toy that inherits common fields and behavior from Toy.
public class EducationalToy extends Toy {

    /** Creates an educational toy without an uploaded image. */
    // Initializes the shared toy fields and sets this toy's type to "Educational".
    public EducationalToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity) {
        // Passes the supplied details and the fixed type to the parent Toy constructor.
        super(id, name, brand, "Educational", ageGroup, price, stockQuantity);
    }

    /** Creates an educational toy with an optional stored image filename. */
    // Initializes the toy with the supplied image filename as well as its other details.
    public EducationalToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity, String imageFilename) {
        // Passes all toy details, including the image filename, to the parent constructor.
        super(id, name, brand, "Educational", ageGroup, price, stockQuantity, imageFilename);
    }

    /** Describes the toy's learning purpose for display in the store. */
    // Replaces the parent implementation with a description specific to educational toys.
    @Override
    public String displayDetails() {
        // Builds the display text using this toy's name and brand.
        return "Educational Toy: " + getName() + " by " + getBrand() + ". Focuses on learning and development.";
    }

    /** Provides the safety warning shown for this toy type. */
    // Implements the safety information method defined by the parent class.
    @Override
    public String getSafetyInfo() {
        // Returns the warning associated with small parts and the minimum age.
        return "Warning: Choking hazard - small parts. Not for children under 3 years.";
    }

    /** Returns the file-format type token used to restore this subtype. */
    // Implements the method that identifies this toy subtype.
    @Override
    public String getToyType() {
        // Returns the type token used to identify educational toys.
        return "EDUCATIONAL";
    }
}
