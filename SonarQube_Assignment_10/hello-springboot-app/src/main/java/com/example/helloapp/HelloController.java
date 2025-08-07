package com.example.helloapp;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // Normal endpoint
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello this is my Java application.";
    }

    // Will trigger S2068 (Hardcoded credentials)
     @GetMapping("/login")
    public String login() {
        String password = "admin123";
        String dbUrl = "jdbc:mysql://localhost:3306/db?user=root&password=root";
        return "Logged in with password: " + password;
    }

    // Will trigger S6418 (Hardcoded API key)
    @GetMapping("/payment")
    public String processPayment() {
        String apiKey = "sk_live_1234567890abcdef";
        return "Processed with key: " + apiKey;
    }

    // Will trigger S4502 (Missing CSRF protection)
    @PostMapping("/transfer")
    public String moneyTransfer() {
        return "Transfer completed";
    }

    // Will trigger S5326 (Plaintext password storage simulation)
    @GetMapping("/storePassword")
    public String storePassword() {
        String userPassword = "plaintextPassword";
        // Simulate storage
        return "Stored password: " + userPassword.hashCode();
    }

    // Duplication examples (will trigger S4144)
    @GetMapping("/report1")
    public String generateReport1() {
        // 10+ line block
        List<String> data = List.of("A", "B", "C");
        StringBuilder output = new StringBuilder();
        output.append("Report 1\n--------\n");
        for (String item : data) {
            output.append("- ").append(item).append("\n");
        }
        return output.toString();
    }

    @GetMapping("/report2")
    public String generateReport2() {
        // Duplicate of report1 (10+ lines)
        List<String> data = List.of("X", "Y", "Z");
        StringBuilder output = new StringBuilder();
        output.append("Report 2\n--------\n");
        for (String item : data) {
            output.append("- ").append(item).append("\n");
        }
        return output.toString();
    }

    // Bug example - will be detected in Bugs section
    @GetMapping("/bug")
    public int triggerBug() {
        int a = 10 / 0; // Bug - division by zero (S3518)
        return a;
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