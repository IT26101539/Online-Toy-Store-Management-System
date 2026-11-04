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

    // HTTP GET endpoint to display the user registration view page
    @GetMapping("/register")
    public String showRegisterPage() {
        // Returns the path to the Thymeleaf registration template
        return "User_Management/register";
    }

    // HTTP POST endpoint to process user registration form submissions
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,@RequestParam String password,
                               @RequestParam String email,@RequestParam String membershipType,
                               RedirectAttributes redirectAttributes) {
        try {
            // Invoke service method to register a new regular user with provided details
            userService.registerRegularUser(username,password,email,membershipType);
            // Add a success flash message to be displayed on the redirected login page
            redirectAttributes.addFlashAttribute("Success Message", "Registration successful. Please login.");
            // Redirect user to the login page on successful registration
            return "redirect:/users/login";
        } catch (Exception e) {
            // Catch any registration exceptions, store error message, and redirect back to registration form
            redirectAttributes.addFlashAttribute("Error Message",e.getMessage());
            return "redirect:/users/register";
        }
    }

    // HTTP GET endpoint to display the user login view page
    @GetMapping("/login")
    public String showLoginPage() {
        // Returns the path to the Thymeleaf login template
        return "User_Management/login";
    }

    // HTTP POST endpoint to handle unified authentication for both regular Users and Admins
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,@RequestParam String password,HttpSession session,RedirectAttributes redirectAttributes) {
        try {
            // Attempt to authenticate credentials as a regular user first
            User user =userService.login(username, password);
            // Initialize and store user details in the HTTP session
            sessionUtil.loginUser(session,user.getId(),user.getUsername(),user.getRole());
            // Add a welcoming success message for the regular user
            redirectAttributes.addFlashAttribute("Success Message","Welcome back, " + user.getUsername() + "!");
            // Redirect authenticated regular user to the main homepage
            return "redirect:/";
        } catch (Exception e) {
            // If regular user authentication fails,try authenticating as an Admin as a fallback
            try {
                com.toystore.Admin_Management.model.Admin admin =adminService.login(username,password);
                // Initialize and store admin details in the HTTP session
                sessionUtil.loginUser(session,admin.getId(),admin.getUsername(),admin.getRole());
                // Add welcoming success message for the admin
                redirectAttributes.addFlashAttribute("successMessage","Welcome back, Admin " +admin.getUsername() + "!");
                // Log the admin login action for auditing purposes
                adminService.logAction(admin.getId(),"Logged in");
                // Redirect authenticated admin to the admin dashboard
                return "redirect:/admin/dashboard";
            } catch (Exception ex) {
                // If both regular user and admin logins fail, set error message and redirect back to login page
                redirectAttributes.addFlashAttribute("errorMessage","Invalid username or password");
                return "redirect:/users/login";
            }
        }
    }

    // HTTP GET endpoint to clear active session data and log out the user
    @GetMapping("/logout")
    public String logoutUser(HttpSession session,RedirectAttributes redirectAttributes) {
        // Clear all attributes associated with the current user session
        sessionUtil.logoutUser(session);
        // Add a success message indicating successful logout
        redirectAttributes.addFlashAttribute("Success Message", "Logged out successfully.");
        // Redirect user back to the public homepage
        return "redirect:/";
    }