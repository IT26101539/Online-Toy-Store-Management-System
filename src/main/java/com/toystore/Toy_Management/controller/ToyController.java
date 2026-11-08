package com.toystore.Toy_Management.controller;

import com.toystore.common.SessionUtil;
import com.toystore.Toy_Management.model.Toy;
import com.toystore.Toy_Management.service.ToyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/** Handles browser requests for browsing and administering the toy catalogue. */
@Controller
@RequestMapping("/toys")
/** Handles web requests for listing, searching, viewing, and administering toys. */
public class ToyController {

    private final ToyService toyService;
    private final SessionUtil sessionUtil;

    /** Connects request handlers to toy operations and session-based admin checks. */
    @Autowired
    public ToyController(ToyService toyService, SessionUtil sessionUtil) {
        this.toyService = toyService;
        this.sessionUtil = sessionUtil;
    }

    /** Shows all toys or the results matching the optional search query. */
    @GetMapping
    public String listToys(@RequestParam(required = false) String search, Model model) {
        List<Toy> toys;
        if (search != null && !search.isEmpty()) {
            toys = toyService.searchToys(search);
            model.addAttribute("searchQuery", search);
        } else {
            toys = toyService.getAllToys();
        }
        model.addAttribute("toys", toys);
        return "Toy_Management/toy-list";
    }
    
    /** Opens the dedicated toy-search page. */
    @GetMapping("/search")
    public String searchPage() {
        return "Toy_Management/toy-search";
    }

    /** Shows one toy's details, returning to the catalogue if it does not exist. */
    @GetMapping("/{id}")
    public String viewToyDetails(@PathVariable String id, Model model) {
        Toy toy = toyService.getToyById(id);
        if (toy == null) {
            return "redirect:/toys";
        }
        model.addAttribute("toy", toy);
        return "Toy_Management/toy-details";
    }

    /** Opens the add form for admins only. */
    @GetMapping("/add")
    public String showAddToyForm(HttpSession session) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        return "Toy_Management/toy-add";
    }

    /** Validates and creates a toy from the submitted form, with an optional image upload. */
    @PostMapping("/add")
    public String addToy(@RequestParam String name, @RequestParam String brand,
                         @RequestParam String type, @RequestParam String ageGroup,
                         @RequestParam double price, @RequestParam int stockQuantity,
                         @RequestParam(value = "image", required = false) MultipartFile image,
                         HttpSession session, RedirectAttributes redirectAttributes) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        
        try {
            if(price < 0 || stockQuantity < 0) {
                 throw new IllegalArgumentException("Price and stock must be positive.");
            }
            toyService.addToy(name, brand, type, ageGroup, price, stockQuantity, image);
            redirectAttributes.addFlashAttribute("successMessage", "Toy added successfully.");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/toys/add";
        }
        return "redirect:/toys";
    }

    /** Opens the edit form for an existing toy when the current user is an admin. */
    @GetMapping("/edit/{id}")
    public String showEditToyForm(@PathVariable String id, HttpSession session, Model model) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        Toy toy = toyService.getToyById(id);
        if (toy == null) return "redirect:/toys";
        model.addAttribute("toy", toy);
        return "Toy_Management/toy-edit";
    }

    /** Updates price, stock, and an optional image for an existing toy. */
    @PostMapping("/edit/{id}")
    public String editToy(@PathVariable String id, @RequestParam double price, 
                          @RequestParam int stockQuantity,
                          @RequestParam(value = "image", required = false) MultipartFile image,
                          HttpSession session, 
                          RedirectAttributes redirectAttributes) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        try {
            if(price < 0 || stockQuantity < 0) {
                 throw new IllegalArgumentException("Price and stock must be positive.");
            }
            Toy toy = toyService.getToyById(id);
            if (toy != null) {
                toy.setPrice(price);
                toy.setStockQuantity(stockQuantity);
                toyService.updateToy(toy, image);
                redirectAttributes.addFlashAttribute("successMessage", "Toy updated successfully.");
            }
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/toys/edit/" + id;
        }
        return "redirect:/toys";
    }

    /** Deletes a toy after confirming the current user is an admin. */
    @PostMapping("/delete/{id}")
    public String deleteToy(@PathVariable String id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        toyService.deleteToy(id);
        redirectAttributes.addFlashAttribute("successMessage", "Toy deleted successfully.");
        return "redirect:/toys";
    }
}
