package com.toystore.Toy_Management.model;

/** Toy subtype for toys that do not require electrical power. */
/** Implements toy behavior and safety information for non-electronic products. */
public class NonElectronicToy extends Toy {

    /** Creates a non-electronic toy without an uploaded image. */
    public NonElectronicToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity) {
        super(id, name, brand, "Non-Electronic", ageGroup, price, stockQuantity);
    }

    /** Creates a non-electronic toy with an optional stored image filename. */
    public NonElectronicToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity, String imageFilename) {
        super(id, name, brand, "Non-Electronic", ageGroup, price, stockQuantity, imageFilename);
    }

    /** Describes the toy's screen-free style of play. */
    @Override
    public String displayDetails() {
        return "Non-Electronic Toy: " + getName() + " by " + getBrand() + ". Classic screen-free tactile play.";
    }

    /** Provides the safety guidance shown for this toy type. */
    @Override
    public String getSafetyInfo() {
        return "Non-toxic materials. Safe for hands-on, active creative play.";
    }

    /** Returns the file-format type token used to restore this subtype. */
    @Override
    public String getToyType() {
        return "NON_ELECTRONIC";
    }
}
