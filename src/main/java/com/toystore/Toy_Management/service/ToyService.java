// Declares the package containing toy-related business logic.
package com.toystore.Toy_Management.service;

// Imports utilities for generating toy IDs and storing uploaded images.
import com.toystore.common.IdGenerator;
import com.toystore.common.ImageStorageUtil;
// Imports the concrete toy subtypes that this service can create.
import com.toystore.Toy_Management.model.EducationalToy;
import com.toystore.Toy_Management.model.ElectronicToy;
import com.toystore.Toy_Management.model.NonElectronicToy;
import com.toystore.Toy_Management.model.PlushToy;
// Imports the shared base type for all toys.
import com.toystore.Toy_Management.model.Toy;
// Imports the repository used to load and save toys.
import com.toystore.Toy_Management.repository.ToyRepository;
// Imports Spring dependency injection and service annotations.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// Imports the type used for uploaded files.
import org.springframework.web.multipart.MultipartFile;

// Imports the exception type used by image storage operations.
import java.io.IOException;
// Imports the list type used to return collections of toys.
import java.util.List;
// Imports stream operations used to filter toys.
import java.util.stream.Collectors;

/** Applies toy-management rules and coordinates persistence and image storage. */
// Marks this class as a Spring service component.
@Service
/** Coordinates toy business operations, repository persistence, and optional image storage. */
public class ToyService {
    // Repository used to save, find, update, and delete toy records.
    private final ToyRepository toyRepository;

    /** Connects the service to the repository that stores toy records. */
    // Injects the repository when Spring creates the service.
    @Autowired
    public ToyService(ToyRepository toyRepository) {
        // Stores the repository for use by the service methods.
        this.toyRepository = toyRepository;
    }

    /** Adds a toy without an image, wrapping any storage I/O error for callers without checked exceptions. */
    // Adds a toy without an image by delegating to the overload that accepts one.
    public void addToy(String name, String brand, String type, String ageGroup, double price, int stockQuantity) {
        // Handles the delegated call and any image storage I/O exception.
        try {
            // Passes null to indicate that no image file was supplied.
            addToy(name, brand, type, ageGroup, price, stockQuantity, null);
        // Converts the checked I/O exception into an unchecked exception.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Creates a typed toy, stores its optional image, and persists the resulting record. */
    // Adds a toy and optionally saves its uploaded image.
    public void addToy(String name, String brand, String type, String ageGroup, double price, int stockQuantity, MultipartFile imageFile) throws IOException {
        // Generates a unique ID for the new toy, using "T" as the ID prefix.
        String id = IdGenerator.generateId("T");
        // Starts with an empty filename for toys without an uploaded image.
        String imageFilename = "";
        // Saves the image only when a file was provided and it contains data.
        if (imageFile != null && !imageFile.isEmpty()) {
            // Stores the image in the toys image directory and records its filename.
            imageFilename = ImageStorageUtil.saveImage("toys", id, imageFile);
        }

        // Holds the concrete toy subtype created from the requested type.
        Toy toy;
        // Creates an educational toy when the type matches this token.
        if ("EDUCATIONAL".equalsIgnoreCase(type)) {
            toy = new EducationalToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename);
        // Creates an electronic toy when the type matches this token.
        } else if ("ELECTRONIC".equalsIgnoreCase(type)) {
            toy = new ElectronicToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename);
        // Accepts either supported spelling of the non-electronic type token.
        } else if ("NON_ELECTRONIC".equalsIgnoreCase(type) || "NON-ELECTRONIC".equalsIgnoreCase(type)) {
            toy = new NonElectronicToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename);
        // Uses the plush subtype when the type matches none of the earlier options.
        } else {
            toy = new PlushToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename);
        }
        // Saves the newly created toy record in the repository.
        toyRepository.save(toy);
    }

    /** Returns every toy currently stored by the repository. */
    // Retrieves all toy records from the repository.
    public List<Toy> getAllToys() {
        return toyRepository.findAll();
    }
    
    /** Returns the matching toy, or null when the identifier is not found. */
    // Looks up a toy by ID and returns null when no match exists.
    public Toy getToyById(String id) {
        return toyRepository.findById(id).orElse(null);
    }

    /** Searches toy names, brands, categories, and age groups without case sensitivity. */
    // Returns toys whose name, brand, category, or age group contains the query.
    public List<Toy> searchToys(String query) {
        // Returns the complete collection when the query is missing or blank.
        if (query == null || query.trim().isEmpty()) {
            return getAllToys();
        }
        // Converts the query to lowercase to make matching case-insensitive.
        String lowerQuery = query.toLowerCase();
        // Searches the toy collection across the supported text fields.
        return getAllToys().stream()
                .filter(t -> t.getName().toLowerCase().contains(lowerQuery) || 
                             t.getBrand().toLowerCase().contains(lowerQuery) ||
                             t.getCategory().toLowerCase().contains(lowerQuery) ||
                             t.getAgeGroup().toLowerCase().contains(lowerQuery))
                // Collects matching toys into a list.
                .collect(Collectors.toList());
    }

    /** Persists changes to a toy without changing its image. */
    // Saves the supplied toy through the repository.
    public void updateToy(Toy toy) {
        toyRepository.update(toy);
    }

    /** Replaces a toy image when supplied, then persists the updated toy record. */
    // Updates the image when a new file is supplied, then saves the toy record.
    public void updateToy(Toy toy, MultipartFile newImageFile) throws IOException {
        // Processes an image only when a non-empty file was supplied.
        if (newImageFile != null && !newImageFile.isEmpty()) {
            // Delete old image if it exists
            // Removes the previous image when the toy has a stored filename.
            if (toy.getImageFilename() != null && !toy.getImageFilename().isEmpty()) {
                ImageStorageUtil.deleteImage("toys", toy.getImageFilename());
            }
            // Saves the replacement image using the toy's ID.
            String newFilename = ImageStorageUtil.saveImage("toys", toy.getId(), newImageFile);
            // Updates the toy with the replacement image filename.
            toy.setImageFilename(newFilename);
        }
        // Saves the toy record, including any updated image filename.
        toyRepository.update(toy);
    }
    
    /** Reduces inventory only when the toy exists and has enough stock for the requested quantity. */
    // Checks available stock and reduces it when sufficient inventory exists.
    public boolean checkAndReduceStock(String toyId, int quantity) {
        // Retrieves the toy whose inventory is being checked.
        Toy toy = getToyById(toyId);
        // Continues only when the toy exists and its stock covers the requested quantity.
        if (toy != null && toy.getStockQuantity() >= quantity) {
            // Subtracts the requested quantity from the current stock.
            toy.setStockQuantity(toy.getStockQuantity() - quantity);
            // Persists the updated stock quantity.
            updateToy(toy);
            // Reports that the stock reduction succeeded.
            return true;
        }
        // Reports that the toy was not found or there was insufficient stock.
        return false;
    }
    
    /** Adds the requested quantity back to stock when the toy exists. */
    // Increases the stock quantity for the identified toy.
    public void increaseStock(String toyId, int quantity) {
        // Retrieves the toy whose inventory should be increased.
        Toy toy = getToyById(toyId);
        // Updates stock only when the toy exists.
        if (toy != null) {
            // Adds the requested quantity to the current stock.
            toy.setStockQuantity(toy.getStockQuantity() + quantity);
            // Persists the updated stock quantity.
            updateToy(toy);
        }
    }

    /** Deletes the toy's stored image and record when the toy exists. */
    // Removes a toy's image and repository record when the toy is found.
    public void deleteToy(String id) {
        // Looks up the toy before attempting to delete its data.
        Toy toy = getToyById(id);
        // Continues only when the requested toy exists.
        if (toy != null) {
            // Deletes the image only when a filename is stored.
            if (toy.getImageFilename() != null && !toy.getImageFilename().isEmpty()) {
                ImageStorageUtil.deleteImage("toys", toy.getImageFilename());
            }
            // Deletes the toy record from the repository.
            toyRepository.delete(id);
        }
    }
}
