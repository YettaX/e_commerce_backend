package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.dto.LoginUserDto;
import com.example.e_commerce_backend.dto.RegisterUserDto;
import com.example.e_commerce_backend.entities.User;
import com.example.e_commerce_backend.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 25/4/2025
 */

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private  final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setFirstName(registerUserDto.getFirstName());
        user.setLastName(registerUserDto.getLastName());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );

        return userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Failed to find user: " + loginUserDto.getEmail()));
    }
}
