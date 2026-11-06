package com.toystore.Supplier_Management.controller;

import com.toystore.common.SessionUtil;
import com.toystore.Supplier_Management.model.Supplier;
import com.toystore.Supplier_Management.service.SupplierService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

// COMPONENT 05: Supplier / Brand Management | Owner: IT26101537
@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    // Service for supplier work and helper to check the admin session
    private final SupplierService supplierService;
    private final SessionUtil sessionUtil;

    // Spring injects the service and session helper (Autowired)
    @Autowired
    public SupplierController(SupplierService supplierService, SessionUtil sessionUtil) {
        this.supplierService = supplierService;
        this.sessionUtil = sessionUtil;
    }

    // Shows the supplier list; filters by name or brand when a search word is given (admin only)
    @GetMapping
    public String listSuppliers(@RequestParam(required = false) String search, HttpSession session, Model model) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        
        List<Supplier> suppliers;
        if (search != null && !search.isEmpty()) {
            suppliers = supplierService.searchSuppliers(search);
            model.addAttribute("searchQuery", search);
        } else {
            suppliers = supplierService.getAllSuppliers();
        }
        model.addAttribute("suppliers", suppliers);
        return "Supplier_Management/supplier-list";
    }

    // Opens the registration page (admin only)
    @GetMapping("/register")
    public String showRegisterForm(HttpSession session) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        return "Supplier_Management/supplier-register";
    }

    // Saves a new supplier from the registration form and shows a success or error message (Create)
    @PostMapping("/register")
    public String registerSupplier(@RequestParam String name, @RequestParam String contact,
                                   @RequestParam String brands, @RequestParam String type,
                                   @RequestParam(value = "image", required = false) org.springframework.web.multipart.MultipartFile image,
                                   HttpSession session, RedirectAttributes redirectAttributes) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        try {
            supplierService.addSupplier(name, contact, brands, type, image);
            redirectAttributes.addFlashAttribute("successMessage", "Supplier added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/suppliers/register";
        }
        return "redirect:/suppliers";
    }
}
