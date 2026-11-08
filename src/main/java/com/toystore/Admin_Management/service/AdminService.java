// This file belongs to the Admin_Management.service package (folder)
package com.toystore.Admin_Management.service;

import com.toystore.common.IdGenerator;
// helper that creates unique IDs

import com.toystore.Admin_Management.model.ActivityLog;
import com.toystore.Admin_Management.model.Admin;
import com.toystore.Admin_Management.model.AdminOperations;
import com.toystore.Admin_Management.repository.ActivityLogRepository;
import com.toystore.Admin_Management.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
// used for hashing

import java.security.NoSuchAlgorithmException;
// thrown if the hash algorithm is missing

import java.time.LocalDateTime;
// current date and time

import java.util.Base64;
// converts bytes into readable text

import java.util.List;
import java.util.Optional;

// COMPONENT 04: Admin Management
// Owner - IT26101539

// @Service: tells Spring this class holds the business logic

// implements AdminOperations: must write registerAdmin() and logAction() (Abstraction)
@Service
public class AdminService implements AdminOperations {

    // The two repositories this service uses to read/write files
    private final AdminRepository adminRepository;
    private final ActivityLogRepository logRepository;

    // Constructor injection: Spring automatically gives the repository objects (@Autowired)
    @Autowired
    public AdminService(AdminRepository adminRepository, ActivityLogRepository logRepository) {
        this.adminRepository = adminRepository;
        this.logRepository = logRepository;
    }

    // Registers a new admin
    @Override
    public void registerAdmin(String username, String password, String email, String permissions) throws Exception {
        // Reject the request if the username is already taken
        if (adminRepository.findByUsername(username).isPresent()) {
            throw new Exception("Admin username already exists");
        }
        String id = IdGenerator.generateId("A");        // unique ID starting with "A"
        String hashedPassword = hashPassword(password); // never store the plain password
        Admin admin = new Admin(id, username, hashedPassword, email, permissions);
        adminRepository.save(admin);                    // write to admins.txt
    }

    // Records an admin's action in the activity log with the current time
    @Override
    public void logAction(String adminId, String action) {
        String timestamp = LocalDateTime.now().toString();
        logRepository.save(new ActivityLog(adminId, action, timestamp));
    }

    // Checks username and password; returns the Admin if correct, otherwise throws an error
    public Admin login(String username, String password) throws Exception {
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            // Hash the entered password and compare it with the stored hash
            if (admin.getPasswordHash().equals(hashPassword(password))) {
                return admin;
            }
        }
        // Same message for wrong username or wrong password (doesn't reveal which one)
        throw new Exception("Invalid admin username or password");
    }

    // Returns all admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Returns one admin by ID, or null if not found
    public Admin getAdminById(String id) {
        return adminRepository.findById(id).orElse(null);
    }

    // Updates an existing admin
    public void updateAdmin(Admin admin) {
        adminRepository.update(admin);
    }

    // Deletes an admin by ID
    public void deleteAdmin(String id) {
        adminRepository.delete(id);
    }

    // Returns all activity logs
    public List<ActivityLog> getLogs() {
        return logRepository.findAll();
    }

    // Private helper: turns a password into a SHA-256 hash (text)
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); // choose the algorithm
            byte[] hash = digest.digest(password.getBytes());            // password -> hash bytes
            return Base64.getEncoder().encodeToString(hash);             // bytes -> text
        } catch (NoSuchAlgorithmException e) {
            // Unchecked exception, so no "throws" is needed
            throw new RuntimeException("Error hashing password", e);
        }
    }
}