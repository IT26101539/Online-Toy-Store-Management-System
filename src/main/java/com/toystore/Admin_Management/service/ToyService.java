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
