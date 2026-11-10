// Declares the package containing toy model classes.
package com.toystore.Toy_Management.model;

/**
 * Shared data and behavior for every toy type in the store.
 * Concrete subclasses provide type-specific descriptions and safety guidance.
 */
/** Defines shared toy fields and behavior used by all concrete toy categories. */
// Abstract base class that requires each toy subtype to provide its own details.
public abstract class Toy {
    // Unique identifier for this toy.
    private String id;
    // Display name of the toy.
    private String name;
    // Brand that manufactured or sells the toy.
    private String brand;
    // Category that identifies the toy type.
    private String category;
    // Intended age range for the toy.
    private String ageGroup;
    // Price of the toy.
    private double price;
    // Number of units currently in stock.
    private int stockQuantity;
    // Filename of the toy's uploaded image, if available.
    private String imageFilename;

    /** Creates a toy without an uploaded image. */
    // Delegates to the full constructor with an empty image filename.
    public Toy(String id, String name, String brand, String category, String ageGroup, double price, int stockQuantity) {
        this(id, name, brand, category, ageGroup, price, stockQuantity, "");
    }

    /** Creates a toy and normalizes a missing image filename to an empty value. */
    // Initializes all shared toy fields.
    public Toy(String id, String name, String brand, String category, String ageGroup, double price, int stockQuantity, String imageFilename) {
        // Stores the toy's unique identifier.
        this.id = id;
        // Stores the toy's display name.
        this.name = name;
        // Stores the toy's brand.
        this.brand = brand;
        // Stores the toy's category.
        this.category = category;
        // Stores the toy's intended age group.
        this.ageGroup = ageGroup;
        // Stores the toy's price.
        this.price = price;
        // Stores the quantity available in stock.
        this.stockQuantity = stockQuantity;
        // Uses an empty string if no image filename was provided.
        this.imageFilename = imageFilename != null ? imageFilename : "";
    }

    // Getters and setters keep toy state encapsulated while allowing the app to read and update it.
    // Gets or updates the toy's unique identifier.
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    // Gets or updates the toy's display name.
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    // Gets or updates the toy's brand.
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    // Gets or updates the toy's category.
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    // Gets or updates the toy's intended age group.
    public String getAgeGroup() { return ageGroup; }
    public void setAgeGroup(String ageGroup) { this.ageGroup = ageGroup; }
    
    // Gets or updates the toy's price.
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    // Gets or updates the number of units available in stock.
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    // Gets or updates the uploaded image filename; a null value becomes an empty string.
    public String getImageFilename() { return imageFilename; }
    public void setImageFilename(String imageFilename) { this.imageFilename = imageFilename != null ? imageFilename : ""; }

    /** Returns the public URL for the uploaded image or the shared placeholder image. */
    // Builds the image URL using the uploaded filename when one is available.
    public String getImageUrl() {
        // Checks that the filename exists and is not blank.
        if (imageFilename != null && !imageFilename.trim().isEmpty()) {
            // Returns the URL for this toy's uploaded image.
            return "/uploads/toys/" + imageFilename;
        }
        // Returns the shared placeholder image URL when no image is available.
        return "/images/placeholder-toy.svg";
    }

    /** Returns a description that each concrete toy type implements for itself. */
    // Requires each subclass to provide a type-specific display description.
    public abstract String displayDetails();

    /** Returns safety guidance specific to this concrete toy type. */
    // Requires each subclass to provide safety information for its toy type.
    public abstract String getSafetyInfo();

    /** Serializes the toy into the pipe-delimited format stored in toys.txt. */
    // Combines the toy fields and type token into one delimited text record.
    public String toFileString() {
        // Returns the fields separated by pipes, using an empty value for a missing image filename.
        return id + "|" + name + "|" + brand + "|" + category + "|" + ageGroup + "|" + price + "|" + stockQuantity + "|" + getToyType() + "|" + (imageFilename != null ? imageFilename : "");
    }
    
    /** Supplies the stable type token used when saving and restoring this toy. */
    // Requires each subclass to provide its type token for file storage and restoration.
    public abstract String getToyType();
}
