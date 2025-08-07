package com.example.helloapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // 1. Will be detected as BUG (S3518 - Division by zero)
    @GetMapping("/bug") 
    public int triggerBug() {
        return 10 / 0;
    }

    // 2. Will be detected as VULNERABILITY (S2068 - Hardcoded password)
    @GetMapping("/login")
    public String login() {
        String password = "admin123"; // Critical vulnerability
        return "Logged in with: " + password;
    }

    // 3. Will be detected as VULNERABILITY (S6437 - Hardcoded API key)
    @GetMapping("/api")
    public String apiEndpoint() {
        String apiKey = "live_1234567890abcdef"; // Critical vulnerability
        return "Using API key: " + apiKey;
    }

    // 4. Will be detected as DUPLICATION (10+ identical lines)
    @GetMapping("/process1")
    public String processOne() {
        // Start of duplicated block (10 lines)
        String result = "";
        for (int i = 0; i < 5; i++) {
            result += "Processing item " + i + "\n";
            result += "Step 1 completed\n";
            result += "Step 2 completed\n";
        }
        result += "Finalizing process\n";
        return result;
        // End of duplicated block
    }

    @GetMapping("/process2")
    public String processTwo() {
        // Duplicate of processOne (10 lines)
        String result = "";
        for (int i = 0; i < 5; i++) {
            result += "Processing item " + i + "\n";
            result += "Step 1 completed\n";
            result += "Step 2 completed\n";
        }
        result += "Finalizing process\n";
        return result;
    }

    // 5. Will be detected as CODE SMELL (S1172 - Unused method)
    public void unusedHelper() {
        System.out.println("This is never used");
    }
}