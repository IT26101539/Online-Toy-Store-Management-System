// This file belongs to the Admin_Management.service package (folder)
package com.toystore.Admin_Management.service;

import com.toystore.common.IdGenerator;   // helper that creates unique IDs
import com.toystore.Admin_Management.model.ActivityLog;
import com.toystore.Admin_Management.model.Admin;
import com.toystore.Admin_Management.model.AdminOperations;
import com.toystore.Admin_Management.repository.ActivityLogRepository;
import com.toystore.Admin_Management.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;              // used for hashing
import java.security.NoSuchAlgorithmException;   // thrown if the hash algorithm is missing
import java.time.LocalDateTime;                  // current date and time
import java.util.Base64;                         // converts bytes into readable text
import java.util.List;
import java.util.Optional;

// COMPONENT 04 - Admin Management
// Owner: IT26101539
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
    public void registerAdmin(String username,String password,String email,String permissions) throws Exception {

        // Reject the request if the username is already taken
        if (adminRepository.findByUsername(username).isPresent()) {
            throw new Exception("Admin username already exists");
        }
        String id = IdGenerator.generateId("A");
        // unique ID starting with "A"

        String hashedPassword = hashPassword(password);
        // never store the plain password

        Admin admin = new Admin(id, username, hashedPassword, email, permissions);
        adminRepository.save(admin);
        // write to admins.txt
    }

