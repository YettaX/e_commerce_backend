package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.dto.UserInfoDto;
import com.example.e_commerce_backend.entities.User;
import com.example.e_commerce_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 27/4/2025
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserInfoDto getUserByEmail(String email) {
        System.out.println("in getUserByEmail service, email: " + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setUserId(user.getUserId());
        userInfoDto.setFullName(user.getFirstName() + " " + user.getLastName());
        return userInfoDto;
    }

}
