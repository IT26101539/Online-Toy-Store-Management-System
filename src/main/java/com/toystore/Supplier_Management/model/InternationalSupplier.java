package com.toystore.Supplier_Management.model;

// COMPONENT 05: Supplier/Brand Management | Owner: IT26101537
public class InternationalSupplier extends Supplier {

    // Calls the Supplier constructor
    public InternationalSupplier(String id,String name,String contact,String brandsSupplied){
        super(id,name,contact,brandsSupplied);
    }

    // Calls the Supplier constructor with the image file name
    public InternationalSupplier(String id,String name,String contact,String brandsSupplied, String imageFilename) {
        super(id,name, contact, brandsSupplied,imageFilename);
    }

    // Polymorphism: international suppliers take longer because of customs
    @Override
    public String getDeliveryTimeEstimate(){
        return "10-15 Business Days(Customs dependent)";
    }

    // Polymorphism: international suppliers cost more
    @Override
    public double getRestockCost(){
        return 20000.0; //Higher cost for international
    }

    // Identifies this supplier as INTERNATIONAL
    @Override
    public String getType() {
        return "INTERNATIONAL";
    }
}
