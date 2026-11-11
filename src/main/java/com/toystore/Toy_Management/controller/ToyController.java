package com.toystore.Toy_Management.controller;

// Utility for working with the current user session.
import com.toystore.common.SessionUtil;

// Toy model and service used by the controller.
import com.toystore.Toy_Management.model.Toy;
import com.toystore.Toy_Management.service.ToyService;

// Servlet session support for storing or retrieving session data.
import jakarta.servlet.http.HttpSession;

// Spring annotations for dependency injection, MVC controllers, and request mapping.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Support for adding uploaded files and passing messages across redirects.
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Imports the List interface for storing and passing collections of toys.
import java.util.List;

/** Handles browser requests for browsing and administering the toy catalogue. */
// Marks this class as a Spring MVC controller that handles web requests.
@Controller
// Sets the base URL path for all request mappings in this controller.
@RequestMapping("/toys")
/** Handles web requests for listing, searching, viewing, and administering toys. */
// Declares the controller class for toy-related web requests.
public class ToyController {

    // Service used to retrieve and manage toy data.
    private final ToyService toyService;
    // Utility used to check the current session's admin status.
    private final SessionUtil sessionUtil;

    /** Connects request handlers to toy operations and session-based admin checks. */
    // Injects the required service and session utility when the controller is created.
    @Autowired
    public ToyController(ToyService toyService, SessionUtil sessionUtil) {
        // Saves the toy service for use by the request handlers.
        this.toyService = toyService;
        // Saves the session utility for checking admin access.
        this.sessionUtil = sessionUtil;
    }

    /** Shows all toys or the results matching the optional search query. */
    // Handles GET requests to /toys.
    @GetMapping
    // Lists all toys, or searches for toys when a search query is provided.
    public String listToys(@RequestParam(required = false) String search, Model model) {
        // Holds the toys that will be displayed on the page.
        List<Toy> toys;
        // If a non-empty search query was provided, retrieve matching toys.
        if (search != null && !search.isEmpty()) {
            // Finds toys whose details match the search query.
            toys = toyService.searchToys(search);
            // Adds the query to the model so the page can display it.
            model.addAttribute("searchQuery", search);
        } else {
            // Retrieves every toy when no search query was provided.
            toys = toyService.getAllToys();
        }
        // Makes the toy list available to the view.
        model.addAttribute("toys", toys);
        // Returns the name of the template used to display the catalogue.
        return "Toy_Management/toy-list";
    }

    /** Opens the dedicated toy-search page. */
    // Handles GET requests to /toys/search.
    @GetMapping("/search")
    // Returns the view containing the toy-search form.
    public String searchPage() {
        // Selects the toy-search template.
        return "Toy_Management/toy-search";
    }

    /** Shows one toy's details, returning to the catalogue if it does not exist. */
    // Handles GET requests to /toys/{id}.
    @GetMapping("/{id}")
    // Retrieves and displays the toy identified by the URL path variable.
    public String viewToyDetails(@PathVariable String id, Model model) {
        // Looks up the toy using the ID from the request URL.
        Toy toy = toyService.getToyById(id);
        // Returns to the catalogue if no toy with this ID exists.
        if (toy == null) {
            // Redirects the browser to the toy catalogue.
            return "redirect:/toys";
        }
        // Makes the toy available to the details view.
        model.addAttribute("toy", toy);
        // Returns the name of the toy-details template.
        return "Toy_Management/toy-details";
    }

    /** Opens the add form for admins only. */
    // Handles GET requests to /toys/add.
    @GetMapping("/add")
    // Shows the form for adding a toy after checking admin access.
    public String showAddToyForm(HttpSession session) {
        // Redirects non-admin users to the home page.
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        // Returns the template containing the add-toy form.
        return "Toy_Management/toy-add";
    }

    /** Validates and creates a toy from the submitted form, with an optional image upload. */
    // Handles form submissions sent as POST requests to /toys/add.
    @PostMapping("/add")
    // Receives the toy form fields, optional image, session, and redirect messages.
    public String addToy(@RequestParam String name, @RequestParam String brand,
                         // Receives the toy type and intended age group from the form.
                         @RequestParam String type, @RequestParam String ageGroup,
                         // Receives the toy's price and available stock quantity.
                         @RequestParam double price, @RequestParam int stockQuantity,
                         // Accepts an optional uploaded image from the form field named "image".
                         @RequestParam(value = "image", required = false) MultipartFile image,
                         // Provides the current session and a way to show messages after redirects.
                         HttpSession session, RedirectAttributes redirectAttributes) {
        // Redirects non-admin users to the home page.
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        // Handles validation, toy creation, and success or error messages.
        try {
            // Rejects negative prices or stock quantities.
            if(price < 0 || stockQuantity < 0) {
                 // Reports the invalid values to the exception handler below.
                 throw new IllegalArgumentException("Price and stock must be positive.");
            }
            // Creates the toy and passes along the optional uploaded image.
            toyService.addToy(name, brand, type, ageGroup, price, stockQuantity, image);
            // Stores a success message to display after redirecting.
            redirectAttributes.addFlashAttribute("successMessage", "Toy added successfully.");
        // Catches validation or service errors so they can be shown on the form.
        } catch(Exception e) {
            // Stores the error message to display after redirecting.
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // Returns to the add form when toy creation fails.
            return "redirect:/toys/add";
        }
        // Returns to the catalogue after the toy is added successfully.
        return "redirect:/toys";
    }

    /** Opens the edit form for an existing toy when the current user is an admin. */
    // Handles GET requests to /toys/edit/{id}.
    @GetMapping("/edit/{id}")
    // Loads the selected toy into the edit form after checking admin access.
    public String showEditToyForm(@PathVariable String id, HttpSession session, Model model) {
        // Redirects non-admin users to the home page.
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        // Retrieves the toy selected by the ID in the URL.
        Toy toy = toyService.getToyById(id);
        // Returns to the catalogue if the requested toy does not exist.
        if (toy == null) return "redirect:/toys";
        // Makes the toy available to the edit view.
        model.addAttribute("toy", toy);
        // Returns the name of the toy-edit template.
        return "Toy_Management/toy-edit";
    }

    /** Updates price, stock, and an optional image for an existing toy. */
    // Handles form submissions sent as POST requests to /toys/edit/{id}.
    @PostMapping("/edit/{id}")
    // Updates the selected toy's price and stock, and optionally its image.
    public String editToy(@PathVariable String id, @RequestParam double price,
                          // Receives the updated stock quantity from the form.
                          @RequestParam int stockQuantity,
                          // Accepts an optional replacement image from the form.
                          @RequestParam(value = "image", required = false) MultipartFile image,
                          // Provides the current session and a way to show redirect messages.
                          HttpSession session,
                          // Carries success or error messages to the next page.
                          RedirectAttributes redirectAttributes) {
        // Redirects non-admin users to the home page.
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        // Handles validation, lookup, update, and redirect messages.
        try {
            // Rejects negative prices or stock quantities.
            if(price < 0 || stockQuantity < 0) {
                 // Reports the invalid values to the exception handler below.
                 throw new IllegalArgumentException("Price and stock must be positive.");
            }
            // Retrieves the toy that should be updated.
            Toy toy = toyService.getToyById(id);
            // Updates the toy only if it still exists.
            if (toy != null) {
                // Applies the submitted price to the toy.
                toy.setPrice(price);
                // Applies the submitted stock quantity to the toy.
                toy.setStockQuantity(stockQuantity);
                // Saves the updated toy and processes the optional image.
                toyService.updateToy(toy, image);
                // Stores a success message to display after redirecting.
                redirectAttributes.addFlashAttribute("successMessage", "Toy updated successfully.");
            }
        // Catches validation or service errors so they can be shown on the edit page.
        } catch(Exception e) {
            // Stores the error message to display after redirecting.
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // Returns to this toy's edit form when the update fails.
            return "redirect:/toys/edit/" + id;
        }
        // Returns to the catalogue after the update completes.
        return "redirect:/toys";
    }

    /** Deletes a toy after confirming the current user is an admin. */
    // Handles form submissions sent as POST requests to /toys/delete/{id}.
    @PostMapping("/delete/{id}")
    // Deletes the toy identified by the URL, provided the user is an admin.
    public String deleteToy(@PathVariable String id, HttpSession session, RedirectAttributes redirectAttributes) {
        // Redirects non-admin users to the home page.
        if (!sessionUtil.isAdmin(session)) return "redirect:/";
        // Removes the selected toy using its ID.
        toyService.deleteToy(id);
        // Stores a success message to display after redirecting.
        redirectAttributes.addFlashAttribute("successMessage", "Toy deleted successfully.");
        // Returns to the catalogue after deletion.
        return "redirect:/toys";
    }
}
