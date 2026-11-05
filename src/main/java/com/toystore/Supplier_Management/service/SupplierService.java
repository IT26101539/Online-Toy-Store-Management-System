package com.toystore.Supplier_Management.service;

import com.toystore.common.IdGenerator;
import com.toystore.Supplier_Management.model.InternationalSupplier;
import com.toystore.Supplier_Management.model.LocalSupplier;
import com.toystore.Supplier_Management.model.Supplier;
import com.toystore.Supplier_Management.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// COMPONENT 05: Supplier / Brand Management | Owner: IT26101537
@Service
public class SupplierService {
    // The service uses the repository for all file operations
    private final SupplierRepository supplierRepository;

    // Spring injects the repository (Autowired)
    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    // Adds a supplier without an image (used by DataInitializer)
    public void addSupplier(String name, String contact, String brands, String type) {
        try {
            addSupplier(name, contact, brands, type, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Creates a new ID, saves the optional logo, then builds a Local or International supplier (Create)
    public void addSupplier(String name, String contact, String brands, String type, org.springframework.web.multipart.MultipartFile imageFile) throws java.io.IOException {
        String id = IdGenerator.generateId("S");
        String imageFilename = "";
        if (imageFile != null && !imageFile.isEmpty()) {
            imageFilename = com.toystore.common.ImageStorageUtil.saveImage("suppliers", id, imageFile);
        }

        Supplier supplier;
        if ("LOCAL".equals(type)) {
            supplier = new LocalSupplier(id, name, contact, brands, imageFilename);
        } else {
            supplier = new InternationalSupplier(id, name, contact, brands, imageFilename);
        }
        supplierRepository.save(supplier);
    }

    // Returns all suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    
    // Returns one supplier, or null when it does not exist
    public Supplier getSupplierById(String id) {
        return supplierRepository.findById(id).orElse(null);
    }

    // Searches by supplier name or brand, ignoring upper and lower case (Read)
    public List<Supplier> searchSuppliers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllSuppliers();
        }
        String lowerQuery = query.toLowerCase();
        return getAllSuppliers().stream()
                .filter(s -> s.getName().toLowerCase().contains(lowerQuery) || 
                             s.getBrandsSupplied().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    // Updates a supplier without changing the logo
    public void updateSupplier(Supplier supplier) {
        supplierRepository.update(supplier);
    }

    // Updates a supplier and replaces its logo when a new one is uploaded (Update)
    public void updateSupplier(Supplier supplier, org.springframework.web.multipart.MultipartFile newImageFile) throws java.io.IOException {
        if (newImageFile != null && !newImageFile.isEmpty()) {
            if (supplier.getImageFilename() != null && !supplier.getImageFilename().isEmpty()) {
                com.toystore.common.ImageStorageUtil.deleteImage("suppliers", supplier.getImageFilename());
            }
            String newFilename = com.toystore.common.ImageStorageUtil.saveImage("suppliers", supplier.getId(), newImageFile);
            supplier.setImageFilename(newFilename);
        }
        supplierRepository.update(supplier);
    }
}
