package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.dto.RegisterUserDto;
import com.example.e_commerce_backend.entities.User;
import com.example.e_commerce_backend.exception.BusinessException;
import com.example.e_commerce_backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 27/4/2025
 */
public class AuthenticationServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Nested
    @DisplayName("Signup Tests")
    class SignupTests {

        @Test
        @DisplayName("Given new user, when signip, then should save user successfully")
        void givenNewUser_whenSignup_thenSaveUser() {

            // Given
            RegisterUserDto registerUserDto = new RegisterUserDto();

            registerUserDto.setEmail("test@example.com");
            registerUserDto.setPassword("test1234");
            registerUserDto.setFirstName("Max");
            registerUserDto.setLastName("Smith");

            when(userRepository.findByEmail(registerUserDto.getEmail())).thenReturn(Optional.empty());
            when(passwordEncoder.encode(registerUserDto.getPassword())).thenReturn("encryptedPassword");
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));



            // When
            User savedUser = authenticationService.signup(registerUserDto);

            // Then
            assertThat(savedUser.getEmail()).isEqualTo(registerUserDto.getEmail());
            assertThat(savedUser.getPassword()).isEqualTo("encryptedPassword");
            assertThat(savedUser.getLastName()).isEqualTo(registerUserDto.getLastName());
            assertThat(savedUser.getFirstName()).isEqualTo(registerUserDto.getFirstName());

            verify(userRepository).findByEmail(anyString());
            verify(userRepository).save(any(User.class));

        }


        @Test
        @DisplayName("Given existing email, When signup, Then should throw a Business exception.")
        void givenExistingEmail_whenSignup_thenThrowBusinessException() {

            // Given
            RegisterUserDto registerUserDto = new RegisterUserDto();
            registerUserDto.setEmail("test@example.com");
            when(userRepository.findByEmail(registerUserDto.getEmail())).thenReturn(Optional.of(new User()));

            // When & Then
            assertThatThrownBy(() -> authenticationService.signup(registerUserDto))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("Email already registered.");

        }
    }
}

