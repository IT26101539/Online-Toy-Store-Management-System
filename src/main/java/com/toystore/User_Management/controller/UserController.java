package com.toystore.User_Management.controller;

// Import necessary common,model,service and Spring framework libraries
import com.toystore.common.SessionUtil;
import com.toystore.User_Management.model.User;
import com.toystore.User_Management.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 // COMPONENT 01: User Management | Owner: IT26101544
 - Controller class to handle all user management requests
 */
@Controller
@RequestMapping("/users") // Base URL prefix for all endpoints defined in this controller class
public class UserController {

    // Declare private service and utility dependencies for business logic and session management
    private final UserService userService;
    private final com.toystore.Admin_Management.service.AdminService adminService;
    private final SessionUtil sessionUtil;

    // Constructor-based dependency injection for Spring to wire dependencies automatically
    @Autowired
    public UserController(UserService userService,com.toystore.Admin_Management.service.AdminService adminService,SessionUtil sessionUtil) {
        this.userService =userService;
        this.adminService =adminService;
        this.sessionUtil =sessionUtil;
    }