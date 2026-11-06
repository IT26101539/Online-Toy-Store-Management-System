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
}
