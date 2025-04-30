package com.example.e_commerce_backend.service;

import com.example.e_commerce_backend.dto.LoginUserDto;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    @Nested
    @DisplayName("Authenticate method tests")
    class AuthenticateTests {

        @Test
        @DisplayName("Given an existing loginUser, when authenticate, then return user")
        void givenExistingLoginUser_whenAuthenticate_thenReturnUser() {
            // Given
            LoginUserDto loginUserDto = new LoginUserDto();
            loginUserDto.setEmail("test@example.com");
            loginUserDto.setPassword("test1234");
            when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));

            // When
            User user = authenticationService.authenticate(loginUserDto);

            assertThat(user).isNotNull();
            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        }

        @Test
        @DisplayName("Given an non-existing loginUser, when authenticate, then throw the UserNameNotFound exception.")
        void givenNonExistingLoginUser_whenAuthenticate_ThenThrowUserNameNotFoundException() {
            // Given
            LoginUserDto loginUserDto = new LoginUserDto();
            loginUserDto.setEmail("test@example.com");
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));
            when(userRepository.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.empty());


            // When + Return
            assertThatThrownBy(() -> authenticationService.authenticate(loginUserDto))
                    .isInstanceOf(UsernameNotFoundException.class)
                    .hasMessageContaining("Failed to find user");
            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(userRepository).findByEmail(loginUserDto.getEmail());

        }

        @Test
        @DisplayName("Given an non-existing loginUser, when authenticate, then throw the BadCredentialsException exception.")
        void givenNonExistingLoginUser_whenAuthenticate_ThenThrowBadCredentialsException() {
            // Given
            LoginUserDto loginUserDto = new LoginUserDto();
            loginUserDto.setEmail("test@example.com");

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(BadCredentialsException.class);

            // When + Return
            assertThatThrownBy(() -> authenticationService.authenticate(loginUserDto))
                    .isInstanceOf(BadCredentialsException.class);

            verify(userRepository, never()).findByEmail(loginUserDto.getEmail());

        }
    }
}

