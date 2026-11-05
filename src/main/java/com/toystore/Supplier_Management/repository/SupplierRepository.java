package com.toystore.Supplier_Management.repository;

import com.toystore.common.FileHandler;
import com.toystore.Supplier_Management.model.InternationalSupplier;
import com.toystore.Supplier_Management.model.LocalSupplier;
import com.toystore.Supplier_Management.model.Supplier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// COMPONENT 05: Supplier / Brand Management | Owner: IT26101537
@Repository
public class SupplierRepository {
    // Helper class that reads and writes suppliers.txt
    private final FileHandler fileHandler;

    // Opens the suppliers.txt file
    public SupplierRepository() {
        this.fileHandler = new FileHandler("suppliers.txt");
    }

    // Adds one supplier to the end of the file (Create)
    public synchronized void save(Supplier supplier) {
        fileHandler.appendLine(supplier.toFileString());
    }

    // Reads every line of the file and builds LocalSupplier or InternationalSupplier objects (Read)
    public synchronized List<Supplier> findAll() {
        List<String> lines = fileHandler.readAllLines();
        List<Supplier> suppliers = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 5) {
                String imageFilename = (parts.length >= 6) ? parts[5] : "";
                if ("LOCAL".equals(parts[4])) {
                    suppliers.add(new LocalSupplier(parts[0], parts[1], parts[2], parts[3], imageFilename));
                } else {
                    suppliers.add(new InternationalSupplier(parts[0], parts[1], parts[2], parts[3], imageFilename));
                }
            }
        }
        return suppliers;
    }

    public Optional<Supplier> findById(String id) {
        return findAll().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }
}
