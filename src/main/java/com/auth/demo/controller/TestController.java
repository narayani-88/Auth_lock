package com.auth.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/user/test")
    public String userTest() {
        return "User authenticated";
    }

    @GetMapping("/admin/test")
    public String adminTest() {
        return "Admin authenticated";
    }
}