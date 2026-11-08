package com.toystore.Toy_Management.model;

/** Toy subtype for battery-powered or otherwise electronic products. */
/** Implements toy behavior and safety information for electronic products. */
public class ElectronicToy extends Toy {

    /** Creates an electronic toy without an uploaded image. */
    public ElectronicToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity) {
        super(id, name, brand, "Electronic", ageGroup, price, stockQuantity);
    }

    /** Creates an electronic toy with an optional stored image filename. */
    public ElectronicToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity, String imageFilename) {
        super(id, name, brand, "Electronic", ageGroup, price, stockQuantity, imageFilename);
    }

    /** Describes the electronic toy and its power requirement. */
    @Override
    public String displayDetails() {
        return "Electronic Toy: " + getName() + " by " + getBrand() + ". Requires batteries.";
    }

    /** Provides the battery safety caution shown for this toy type. */
    @Override
    public String getSafetyInfo() {
        return "Caution: Contains button or coin cell battery. Hazardous if swallowed.";
    }

    /** Returns the file-format type token used to restore this subtype. */
    @Override
    public String getToyType() {
        return "ELECTRONIC";
    }
}
