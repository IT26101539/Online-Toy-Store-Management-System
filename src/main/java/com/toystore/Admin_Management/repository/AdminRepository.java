package com.toystore.Admin_Management.repository;

import com.toystore.common.FileHandler;
import com.toystore.Admin_Management.model.Admin;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


// COMPONENT 04: Admin Management 
// Owner - Placeholder
@Repository
public class AdminRepository {
    private final FileHandler fileHandler;

    public AdminRepository() {
        this.fileHandler = new FileHandler("admins.txt");
    }

    public synchronized void save(Admin admin) {
        fileHandler.appendLine(admin.toFileString());
    }

    public synchronized List<Admin> findAll() {
        List<String> lines = fileHandler.readAllLines();
        List<Admin> admins = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 6) {
                admins.add(new Admin(parts[0], parts[1], parts[2], parts[3], parts[5]));
            }
        }
        return admins;
    }