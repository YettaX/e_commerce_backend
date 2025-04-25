package com.example.e_commerce_backend.controller;

import com.example.e_commerce_backend.dto.LoginResponse;
import com.example.e_commerce_backend.dto.LoginUserDto;
import com.example.e_commerce_backend.dto.RegisterUserDto;
import com.example.e_commerce_backend.entities.User;
import com.example.e_commerce_backend.service.AuthenticationService;
import com.example.e_commerce_backend.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 25/4/2025
 */

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(Map.of());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        System.out.println("LoginUserDto: " + loginUserDto.toString());
        User user = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);

    }
}
