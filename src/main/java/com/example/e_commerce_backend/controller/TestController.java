package com.example.e_commerce_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 25/4/2025
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok("pass");
    }
}
