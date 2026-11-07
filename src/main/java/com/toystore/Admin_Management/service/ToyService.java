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