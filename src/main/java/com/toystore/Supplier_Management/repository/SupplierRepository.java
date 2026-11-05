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

    public synchronized void save(Supplier supplier) {
        fileHandler.appendLine(supplier.toFileString());
    }
}
