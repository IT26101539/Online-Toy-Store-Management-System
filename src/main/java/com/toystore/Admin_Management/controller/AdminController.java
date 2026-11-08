// This file belongs to the Admin_Management.controller package (folder)
package com.toystore.Admin_Management.controller;

import com.toystore.common.SessionUtil;
// helper to check who is logged in

import com.toystore.User_Management.service.UserService;
// used to count users

import com.toystore.Toy_Management.service.ToyService;
// used to count toys

import com.toystore.Order_Management.service.OrderService;
// used to count orders

import com.toystore.Admin_Management.model.Admin;

import com.toystore.Admin_Management.service.AdminService;

import jakarta.servlet.http.HttpSession;
// the user's session (login info)

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
// carries data from controller to HTML page

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
// carries messages across a redirect

// COMPONENT 04 - Admin Management
// Owner - IT26101539
// @Controller: this class handles web requests and returns HTML page names
// @RequestMapping("/admin"): every URL in this class starts with /admin
@Controller
@RequestMapping("/admin")
public class AdminController {

    // The objects this controller depends on (final = set once, never changed)
    private final AdminService adminService;
    private final UserService userService;
    private final ToyService toyService;
    private final OrderService orderService;
    private final SessionUtil sessionUtil;

    // Constructor injection: Spring creates these objects and passes them in (@Autowired)
    @Autowired
    public AdminController(AdminService adminService, UserService userService,
                           ToyService toyService, OrderService orderService, SessionUtil sessionUtil) {
        this.adminService = adminService;
        this.userService = userService;
        this.toyService = toyService;
        this.orderService = orderService;
        this.sessionUtil = sessionUtil;
    }

    // Handles GET /admin/dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        // Security check: only admins may enter, others are sent to the home page
        if (!sessionUtil.isAdmin(session)) return "redirect:/";

        // Send the 3 counts to dashboard.html
        // (used as ${userCount}, ${toyCount}, ${orderCount})
        model.addAttribute("userCount", userService.getAllUsers().size());
        model.addAttribute("toyCount", toyService.getAllToys().size());
        model.addAttribute("orderCount", orderService.getAllOrders().size());

        // Show templates/Admin_Management/dashboard.html
        return "Admin_Management/dashboard";
    }

    // Handles GET /admin/list
    @GetMapping("/list")
    public String listAdmins(HttpSession session, Model model) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";

        // Send admins and logs to admin-panel.html (used as ${admins} and ${logs})
        model.addAttribute("admins", adminService.getAllAdmins());
        model.addAttribute("logs", adminService.getLogs());
        return "Admin_Management/admin-panel";
    }

    // Handles GET /admin/register : just shows the empty form
    @GetMapping("/register")
    public String showRegisterForm(HttpSession session) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        return "Admin_Management/admin-register";
    }

    // Handles POST /admin/register : runs when the form is submitted
    // @RequestParam: reads form fields by name (must match name="..." in the HTML)
    @PostMapping("/register")
    public String registerAdmin(@RequestParam String username, @RequestParam String password,
                                @RequestParam String email, @RequestParam String permissions,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        try {
            // Business logic is done in the service layer
            adminService.registerAdmin(username, password, email, permissions);
            // Record who did this action in the activity log
            adminService.logAction(sessionUtil.getLoggedInUserId(session), "Registered new admin: " + username);
            // Flash attribute = message that survives one redirect, then disappears
            redirectAttributes.addFlashAttribute("successMessage", "Admin registered successfully.");
        } catch (Exception e) {
            // e.g. "Admin username already exists": show it and go back to the form
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/register";
        }
        // Success: go to the admin list
        return "redirect:/admin/list";
    }

    // Handles POST /admin/delete/{id} : {id} is read from the URL by @PathVariable
    @PostMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable String id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";

        // Prevent an admin from deleting their own account
        if (sessionUtil.getLoggedInUserId(session).equals(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You cannot delete your own admin account.");
            return "redirect:/admin/list";
        }

        adminService.deleteAdmin(id);   // remove from admins.txt
        adminService.logAction(sessionUtil.getLoggedInUserId(session), "Deleted admin ID: " + id);
        redirectAttributes.addFlashAttribute("successMessage", "Admin deleted successfully.");
        return "redirect:/admin/list";
    }
}