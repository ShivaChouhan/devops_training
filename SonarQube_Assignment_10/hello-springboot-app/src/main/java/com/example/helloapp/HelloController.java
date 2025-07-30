package com.example.helloapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final String DUMMY = "SuperSecret123!";
    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("Dummy  " + DUMMY); // Logging the dummy password
        return "Hello this is my java application."; // Changed to a more friendly greeting
    }
}