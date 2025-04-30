package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.dto.UserInfoDto;
import com.example.e_commerce_backend.entities.User;
import com.example.e_commerce_backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 30/4/2025
 */
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("getUserByEmail tests")
    class GetUserByEmailTests {

        @Test
        @DisplayName("Given a valid email, when getUserByEmail, then return userInfoDto")
        public void givenValidEmail_whenGetUserByEmail_thenReturnUserInfoDto(){
            // Given
            String email = "test@example.com";
            User user = new User();
            user.setEmail(email);
            when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

            // When
            UserInfoDto userInfoDto = userService.getUserByEmail(email);

            // Return
            assertThat(userInfoDto).isNotNull();

        }

        @Test
        @DisplayName("Given a invalid email, when getUserByEmail, then throw UsernameNotFound exception")
        public void givenInvalidEmail_whenGetUserByEmail_thenThrowUsernameNotFoundException() {
            // Given
            String email = "test@example.com";
            when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

            // When + Return
            assertThatThrownBy(() -> userService.getUserByEmail(email)).isInstanceOf(UsernameNotFoundException.class);

        }
    }
}
