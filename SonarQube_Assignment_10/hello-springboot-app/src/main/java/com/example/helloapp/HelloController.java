package com.example.helloapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // Normal endpoint
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello this is my Java application.";
    }

    // Bug example - will be detected in Bugs section
    @GetMapping("/bug")
    public int triggerBug() {
        int a = 10 / 0; // Bug - division by zero (S3518)
        return a;
    }

    // Vulnerability example - will be detected in Vulnerabilities section
    @GetMapping("/login")
    public String login() {
        String password = "admin123"; // Vulnerability - hardcoded password (S2068)
        String apiKey = "1234-5678-9012"; // Vulnerability - hardcoded API key (S6418)
        return "Logged in with password: " + password;
    }

    // Code smell example - unused method
    public void unusedMethod() {
        int x = 42; // Unused variable (S1481)
        System.out.println("This method is never used");
    }

    // Duplication example (more substantial to trigger detection)
    @GetMapping("/sum1")
    public String duplicateLogic1() {
        int[] numbers = {1, 2, 3, 4, 5};
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return "Total1: " + total;
    }

    @GetMapping("/sum2")
    public String duplicateLogic2() {
        int[] numbers = {1, 2, 3, 4, 5};
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return "Total2: " + total;
    }

    // Another duplication pattern
    @GetMapping("/processA")
    public String processA() {
        String input = "demo";
        String result = input.toUpperCase();
        result = result.trim();
        result = result.concat("_A");
        return result;
    }

    @GetMapping("/processB")
    public String processB() {
        String input = "test";
        String result = input.toUpperCase();
        result = result.trim();
        result = result.concat("_B");
        return result;
    }
}