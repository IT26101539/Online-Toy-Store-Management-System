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