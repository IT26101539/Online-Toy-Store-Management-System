package com.toystore.Supplier_Management.model;

// COMPONENT 05: Supplier / Brand Management | Owner: IT26101537
public class LocalSupplier extends Supplier {

    // Calls the Supplier constructor
    public LocalSupplier(String id, String name, String contact, String brandsSupplied) {
        super(id, name, contact, brandsSupplied);
    }

    // Calls the Supplier constructor with the image file name
    public LocalSupplier(String id, String name, String contact, String brandsSupplied, String imageFilename) {
        super(id, name, contact, brandsSupplied, imageFilename);
    }

    // Polymorphism: local suppliers deliver quickly
    @Override
    public String getDeliveryTimeEstimate() {
        return "1-3 Business Days";
    }

    // Polymorphism: local suppliers have a fixed low cost
    @Override
    public double getRestockCost() {
        return 5000.0; // Fixed flat rate for local
    }

    // Identifies this supplier as LOCAL
    @Override
    public String getType() {
        return "LOCAL";
    }
}
