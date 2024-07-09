package com.lecture8.assignment1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    
    @GetMapping("/")
    public String home() {
        return "Welcome!";
    }

}
