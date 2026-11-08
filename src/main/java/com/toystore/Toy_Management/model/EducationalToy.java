package com.toystore.Toy_Management.model;

/** Toy subtype for products intended to support learning and development. */
/** Implements toy behavior and safety information for educational products. */
public class EducationalToy extends Toy {

    /** Creates an educational toy without an uploaded image. */
    public EducationalToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity) {
        super(id, name, brand, "Educational", ageGroup, price, stockQuantity);
    }

    /** Creates an educational toy with an optional stored image filename. */
    public EducationalToy(String id, String name, String brand, String ageGroup, double price, int stockQuantity, String imageFilename) {
        super(id, name, brand, "Educational", ageGroup, price, stockQuantity, imageFilename);
    }

    /** Describes the toy's learning purpose for display in the store. */
    @Override
    public String displayDetails() {
        return "Educational Toy: " + getName() + " by " + getBrand() + ". Focuses on learning and development.";
    }

    /** Provides the safety warning shown for this toy type. */
    @Override
    public String getSafetyInfo() {
        return "Warning: Choking hazard - small parts. Not for children under 3 years.";
    }

    /** Returns the file-format type token used to restore this subtype. */
    @Override
    public String getToyType() {
        return "EDUCATIONAL";
    }
}
