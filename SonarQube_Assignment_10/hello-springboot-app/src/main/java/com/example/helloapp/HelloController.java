package com.example.helloapp;

import java.security.MessageDigest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // Normal endpoint
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello this is my Java application.";
    }

    
    public int triggerBug() {
        int a = 10 / 0; // Bug - division by zero
        return a;
    }

    
    public String login() {
        String password = "admin123"; // Vulnerability - hardcoded password
        return "Logged in with password: " + password;
    }

    
    public String vulnerableSQL(@RequestParam String userInput) {
        String query = "SELECT * FROM users WHERE username = '" + userInput + "'";
        return "Running query: " + query; // SQL injection risk
    }

    
    public void unusedMethod() {
        int x = 42; // Unused variable
    }

    
    public String duplicateLogic1() {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += i;
        }
        return "Sum1: " + sum;
    }

    public String duplicateLogic2() {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += i;
        }
        return "Sum2: " + sum;
    }

    public String weakEncryption() throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5"); // Weak encryption
        md.update("password".getBytes());
        byte[] digest = md.digest();
        return new String(digest);
    }
}
