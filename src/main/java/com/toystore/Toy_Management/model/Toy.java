package com.toystore.Toy_Management.model;

/**
 * Shared data and behavior for every toy type in the store.
 * Concrete subclasses provide type-specific descriptions and safety guidance.
 */
/** Defines shared toy fields and behavior used by all concrete toy categories. */
public abstract class Toy {
    private String id;
    private String name;
    private String brand;
    private String category;
    private String ageGroup;
    private double price;
    private int stockQuantity;
    private String imageFilename;

    /** Creates a toy without an uploaded image. */
    public Toy(String id, String name, String brand, String category, String ageGroup, double price, int stockQuantity) {
        this(id, name, brand, category, ageGroup, price, stockQuantity, "");
    }

    /** Creates a toy and normalizes a missing image filename to an empty value. */
    public Toy(String id, String name, String brand, String category, String ageGroup, double price, int stockQuantity, String imageFilename) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.ageGroup = ageGroup;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageFilename = imageFilename != null ? imageFilename : "";
    }

    // Getters and setters keep toy state encapsulated while allowing the app to read and update it.
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getAgeGroup() { return ageGroup; }
    public void setAgeGroup(String ageGroup) { this.ageGroup = ageGroup; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getImageFilename() { return imageFilename; }
    public void setImageFilename(String imageFilename) { this.imageFilename = imageFilename != null ? imageFilename : ""; }

    /** Returns the public URL for the uploaded image or the shared placeholder image. */
    public String getImageUrl() {
        if (imageFilename != null && !imageFilename.trim().isEmpty()) {
            return "/uploads/toys/" + imageFilename;
        }
        return "/images/placeholder-toy.svg";
    }

    /** Returns a description that each concrete toy type implements for itself. */
    public abstract String displayDetails();

    /** Returns safety guidance specific to this concrete toy type. */
    public abstract String getSafetyInfo();

    /** Serializes the toy into the pipe-delimited format stored in toys.txt. */
    public String toFileString() {
        return id + "|" + name + "|" + brand + "|" + category + "|" + ageGroup + "|" + price + "|" + stockQuantity + "|" + getToyType() + "|" + (imageFilename != null ? imageFilename : "");
    }
    
    /** Supplies the stable type token used when saving and restoring this toy. */
    public abstract String getToyType();
}
