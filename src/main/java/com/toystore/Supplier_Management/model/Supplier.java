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

    // Creates a supplier with no logo by calling the main constructor
    public Supplier(String id, String name, String contact, String brandsSupplied) {
        this(id, name, contact, brandsSupplied, "");
    }

    // Main constructor: sets every field and uses empty text when the image is null
    public Supplier(String id, String name, String contact, String brandsSupplied, String imageFilename) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.brandsSupplied = brandsSupplied;
        this.imageFilename = imageFilename != null ? imageFilename : "";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getBrandsSupplied() { return brandsSupplied; }
    public void setBrandsSupplied(String brandsSupplied) { this.brandsSupplied = brandsSupplied; }
}
