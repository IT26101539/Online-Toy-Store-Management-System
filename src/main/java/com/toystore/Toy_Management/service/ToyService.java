package com.toystore.Toy_Management.service;

import com.toystore.common.IdGenerator;
import com.toystore.common.ImageStorageUtil;
import com.toystore.Toy_Management.model.EducationalToy;
import com.toystore.Toy_Management.model.ElectronicToy;
import com.toystore.Toy_Management.model.NonElectronicToy;
import com.toystore.Toy_Management.model.PlushToy;
import com.toystore.Toy_Management.model.Toy;
import com.toystore.Toy_Management.repository.ToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/** Applies toy-management rules and coordinates persistence and image storage. */
@Service
/** Coordinates toy business operations, repository persistence, and optional image storage. */
public class ToyService {
    private final ToyRepository toyRepository;

    /** Connects the service to the repository that stores toy records. */
    @Autowired
    public ToyService(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    /** Adds a toy without an image, wrapping any storage I/O error for callers without checked exceptions. */
    public void addToy(String name, String brand, String type, String ageGroup, double price, int stockQuantity) {
        try {
            addToy(name, brand, type, ageGroup, price, stockQuantity, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Creates a typed toy, stores its optional image, and persists the resulting record. */
    public void addToy(String name, String brand, String type, String ageGroup, double price, int stockQuantity, MultipartFile imageFile) throws IOException {
        String id = IdGenerator.generateId("T");
        String imageFilename = "";
        if (imageFile != null && !imageFile.isEmpty()) {
            imageFilename = ImageStorageUtil.saveImage("toys", id, imageFile);
        }

        Toy toy;
        if ("EDUCATIONAL".equalsIgnoreCase(type)) {
            toy = new EducationalToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename);
        } else if ("ELECTRONIC".equalsIgnoreCase(type)) {
            toy = new ElectronicToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename);
        } else if ("NON_ELECTRONIC".equalsIgnoreCase(type) || "NON-ELECTRONIC".equalsIgnoreCase(type)) {
            toy = new NonElectronicToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename);
        } else {
            toy = new PlushToy(id, name, brand, ageGroup, price, stockQuantity, imageFilename);
        }
        toyRepository.save(toy);
    }

    /** Returns every toy currently stored by the repository. */
    public List<Toy> getAllToys() {
        return toyRepository.findAll();
    }
    
    /** Returns the matching toy, or null when the identifier is not found. */
    public Toy getToyById(String id) {
        return toyRepository.findById(id).orElse(null);
    }

    /** Searches toy names, brands, categories, and age groups without case sensitivity. */
    public List<Toy> searchToys(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllToys();
        }
        String lowerQuery = query.toLowerCase();
        return getAllToys().stream()
                .filter(t -> t.getName().toLowerCase().contains(lowerQuery) || 
                             t.getBrand().toLowerCase().contains(lowerQuery) ||
                             t.getCategory().toLowerCase().contains(lowerQuery) ||
                             t.getAgeGroup().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    /** Persists changes to a toy without changing its image. */
    public void updateToy(Toy toy) {
        toyRepository.update(toy);
    }

    /** Replaces a toy image when supplied, then persists the updated toy record. */
    public void updateToy(Toy toy, MultipartFile newImageFile) throws IOException {
        if (newImageFile != null && !newImageFile.isEmpty()) {
            // Delete old image if it exists
            if (toy.getImageFilename() != null && !toy.getImageFilename().isEmpty()) {
                ImageStorageUtil.deleteImage("toys", toy.getImageFilename());
            }
            String newFilename = ImageStorageUtil.saveImage("toys", toy.getId(), newImageFile);
            toy.setImageFilename(newFilename);
        }
        toyRepository.update(toy);
    }
    
    /** Reduces inventory only when the toy exists and has enough stock for the requested quantity. */
    public boolean checkAndReduceStock(String toyId, int quantity) {
        Toy toy = getToyById(toyId);
        if (toy != null && toy.getStockQuantity() >= quantity) {
            toy.setStockQuantity(toy.getStockQuantity() - quantity);
            updateToy(toy);
            return true;
        }
        return false;
    }
    
    /** Adds the requested quantity back to stock when the toy exists. */
    public void increaseStock(String toyId, int quantity) {
        Toy toy = getToyById(toyId);
        if (toy != null) {
            toy.setStockQuantity(toy.getStockQuantity() + quantity);
            updateToy(toy);
        }
    }

    /** Deletes the toy's stored image and record when the toy exists. */
    public void deleteToy(String id) {
        Toy toy = getToyById(id);
        if (toy != null) {
            if (toy.getImageFilename() != null && !toy.getImageFilename().isEmpty()) {
                ImageStorageUtil.deleteImage("toys", toy.getImageFilename());
            }
            toyRepository.delete(id);
        }
    }
}
