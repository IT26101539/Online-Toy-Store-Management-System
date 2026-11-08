package com.toystore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
/** Maps the site root URL to the home page template. */
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
