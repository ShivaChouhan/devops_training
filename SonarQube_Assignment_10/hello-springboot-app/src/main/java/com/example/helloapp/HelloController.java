package com.example.helloapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("Hello this is my java application.");
        return "Hello this is my java application."; // Changed to a more friendly greeting
    }

    // 🚨 Vulnerable endpoint (for testing CodeQL)
    @GetMapping("/run")
    public String runCommand(@RequestParam String cmd) throws IOException {
        // ⚠️ CodeQL will flag this as a security issue (command injection)
        Runtime.getRuntime().exec(cmd);
        return "Command executed: " + cmd;
    }
}