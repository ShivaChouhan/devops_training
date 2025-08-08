package com.example.helloapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // 1. Original endpoint
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello this is my Java application.";
    }

    // 2. Now exposed as endpoint (previously unused)
    @GetMapping("/trigger-bug")
    public int triggerBug() {
        int a = 10 / 0; // Will be detected as bug (S3518)
        return a;
    }

    // 3. Now exposed as endpoint (previously unused)
    @GetMapping("/login")
    public String login() {
        String password = "admin123"; // Will be detected as vulnerability (S2068)
        return "Logged in with password: " + password;
    }

    private String calculateSum(String prefix) {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += i;
        }
        return prefix + sum;
    }

    private String calculateSum2(String prefix) {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += i;
        }
        return prefix + sum;
    }
    

    // 5. Duplicate logic endpoints
    @GetMapping("/logic1")
    public String duplicateLogic1() {
        return calculateSum("Sum1: ");
    }

    @GetMapping("/logic2")
    public String duplicateLogic2() {
        return calculateSum2("Sum2: ");
    }

    
}