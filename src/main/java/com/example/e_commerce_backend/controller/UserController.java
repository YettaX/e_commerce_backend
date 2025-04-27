package com.example.e_commerce_backend.controller;

import com.example.e_commerce_backend.dto.UserInfoDto;
import com.example.e_commerce_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 27/4/2025
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public ResponseEntity<UserInfoDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
       String email = userDetails.getUsername();
       UserInfoDto userInfoDto = userService.getUserByEmail(email);
       return ResponseEntity.ok(userInfoDto);
    }
}
