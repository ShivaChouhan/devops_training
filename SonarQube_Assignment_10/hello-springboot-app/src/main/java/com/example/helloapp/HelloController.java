package com.example.helloapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("Hello endpoint was called!"); // Added logging for debugging
        return "Hello this is my java application."; // Changed to a more friendly greeting
        
    }
}