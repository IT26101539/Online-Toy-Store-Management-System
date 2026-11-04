package com.toystore.Supplier_Management.model;

// COMPONENT 05: Supplier / Brand Management | Owner: IT26101537
// Encapsulation and Polymorphism
public abstract class Supplier {
    // Supplier details are private (Encapsulation); other classes use getters and setters
    private String id;
    private String name;
    private String contact;
    private String brandsSupplied;

    // Name of the logo file; empty when no logo is uploaded
    private String imageFilename;
}
