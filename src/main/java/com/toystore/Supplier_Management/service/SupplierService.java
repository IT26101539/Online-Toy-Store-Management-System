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

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }
}
