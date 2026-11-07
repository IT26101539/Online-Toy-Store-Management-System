// This file belongs to the Admin_Management.repository package (folder)
package com.toystore.Admin_Management.repository;

// FileHandler: helper class (common package) that reads/writes text files
import com.toystore.common.FileHandler;
import com.toystore.Admin_Management.model.Admin;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


// COMPONENT 04 - Admin Management
// Owner - IT26101539

// @Repository: Spring manages this class and treats it as the data access layer
@Repository
public class AdminRepository {

    // Helper object used to read/write the admins text file
    private final FileHandler fileHandler;


    // Constructor - links this repository to the file "admins.txt
    public AdminRepository() {
        this.fileHandler = new FileHandler("admins.txt");
    }

    // CREATE - adds one admin as a new line at the end of the file
    // synchronized = only one thread at a time (prevents file corruption)
    public synchronized void save(Admin admin) {
        fileHandler.appendLine(admin.toFileString());
    }

    // READ ALL: reads every line and converts each into an Admin object
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