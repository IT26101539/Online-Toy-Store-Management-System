// Declares the package containing toy model classes.
package com.toystore.Toy_Management.model;

/** Toy subtype for battery-powered or otherwise electronic products. */
/** Implements toy behavior and safety information for electronic products. */
// Defines an electronic toy that inherits common fields and behavior from Toy.
public class ElectronicToy extends Toy {

    /** Creates an electronic toy without an uploaded image. */
    // Initializes the toy with its details and the fixed "Electronic" type.
    public ElectronicToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity) {
        // Passes the supplied details and type to the parent Toy constructor.
        super(id, name, brand, "Electronic", ageGroup, price, stockQuantity);
    }

    /** Creates an electronic toy with an optional stored image filename. */
    // Initializes the toy with its details and an image filename.
    public ElectronicToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity, String imageFilename) {
        // Passes all details, including the image filename, to the parent constructor.
        super(id, name, brand, "Electronic", ageGroup, price, stockQuantity, imageFilename);
    }

    /** Describes the electronic toy and its power requirement. */
    // Overrides the parent method with a description for electronic toys.
    @Override
    public String displayDetails() {
        // Builds the display text using this toy's name and brand.
        return "Electronic Toy: " + getName() + " by " + getBrand() + ". Requires batteries.";
    }

    /** Provides the battery safety caution shown for this toy type. */
    // Implements the safety information method defined by the parent class.
    @Override
    public String getSafetyInfo() {
        // Returns the caution about the hazards of button or coin cell batteries.
        return "Caution: Contains button or coin cell battery. Hazardous if swallowed.";
    }

    /** Returns the file-format type token used to restore this subtype. */
    // Implements the method that identifies this toy subtype.
    @Override
    public String getToyType() {
        // Returns the type token used to identify electronic toys.
        return "ELECTRONIC";
    }
}
