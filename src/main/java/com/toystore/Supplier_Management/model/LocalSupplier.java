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

    @Override
    public String getDeliveryTimeEstimate() {
        return "1-3 Business Days";
    }
}
