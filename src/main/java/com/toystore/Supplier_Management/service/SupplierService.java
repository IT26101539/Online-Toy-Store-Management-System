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
    
    public Supplier getSupplierById(String id) {
        return supplierRepository.findById(id).orElse(null);
    }
}
