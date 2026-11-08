package com.toystore.Toy_Management.model;

/** Toy subtype for soft, stuffed products. */
/** Implements toy behavior and safety information for plush products. */
public class PlushToy extends Toy {

    /** Creates a plush toy without an uploaded image. */
    public PlushToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity) {
        super(id, name, brand, "Plush", ageGroup, price, stockQuantity);
    }

    /** Creates a plush toy with an optional stored image filename. */
    public PlushToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity, String imageFilename) {
        super(id, name, brand, "Plush", ageGroup, price, stockQuantity, imageFilename);
    }

    /** Describes the soft toy for display in the store. */
    @Override
    public String displayDetails() {
        return "Plush Toy: " + getName() + " by " + getBrand() + ". Soft and cuddly.";
    }

    /** Provides the care and safety guidance shown for this toy type. */
    @Override
    public String getSafetyInfo() {
        return "Safety: Keep away from fire. Remove all tags before giving to a child.";
    }

    /** Returns the file-format type token used to restore this subtype. */
    @Override
    public String getToyType() {
        return "PLUSH";
    }
}
