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

            // Split the line at "|"
            // Format: id|username|passwordHash|email|ROLE|permissions
            String[] parts = line.split("\\|");

            // A valid admin line must have 6 parts
            if (parts.length >= 6) {

                // parts[4] is the role ("ADMIN"), which is skipped because Admin sets it itself
                admins.add(new Admin(parts[0], parts[1], parts[2], parts[3], parts[5]));
            }
        }
        return admins;
    }

    // Finds an admin by username. Optional = the result may or may not exist
    public Optional<Admin> findByUsername(String username) {
        return findAll().stream()                                // turn the list into a stream
                .filter(a -> a.getUsername().equals(username))   // keep only matching usernames
                .findFirst();                                    // take the first match (if any)
    }

    // Finds an admin by ID, same idea as above
    public Optional<Admin> findById(String id) {
        return findAll().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    // UPDATE: replaces the admin that has the same ID, then rewrites the whole file
    public synchronized void update(Admin updatedAdmin) {
        List<Admin> admins = findAll();
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).getId().equals(updatedAdmin.getId())) {
                admins.set(i, updatedAdmin);   // replace the old object with the new one
                break;                         // stop the loop, we found it
            }
        }
        saveAll(admins);
    }

    // DELETE: keeps every admin EXCEPT the one with the given ID, then rewrites the file
    public synchronized void delete(String id) {
        List<Admin> admins = findAll().stream()
                .filter(a -> !a.getId().equals(id))   // "!" = keep those whose ID is NOT this one
                .collect(Collectors.toList());        // collect the result back into a list
        saveAll(admins);
    }

    // Converts all Admin objects to text lines and overwrites the whole file
    private void saveAll(List<Admin> admins) {
        List<String> lines = admins.stream().map(Admin::toFileString).collect(Collectors.toList());
        fileHandler.writeAllLines(lines);
    }
}