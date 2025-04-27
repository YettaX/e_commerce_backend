package com.example.e_commerce_backend.repositories;

import com.example.e_commerce_backend.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 27/4/2025
 */
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;


    @Nested
    @DisplayName("findByEmail Tests")
    class FindByEmailTests {

        @Test
        @DisplayName("Given existing email, when findByEmail, the should return user")
        void givenExistingEmail_whenFindByEmail_thenReturnUser() {

            // Given
            User user = new User();
            String email = "test@example.com";

            user.setEmail(email);
            user.setFirstName("Lily");
            user.setLastName("Smith");
            user.setPassword("test1234");

            userRepository.save(user);

            // When
            Optional<User> foundUser = userRepository.findByEmail(email);

            // Then
            assertThat(foundUser).isPresent();
            assertThat(foundUser.get().getEmail()).isEqualTo(email);
            assertThat(foundUser.get().getFirstName()).isEqualTo(user.getFirstName());
            assertThat(foundUser.get().getLastName()).isEqualTo(user.getLastName());
            assertThat(foundUser.get().getPassword()).isEqualTo(user.getPassword());

        }

        @Test
        @DisplayName("Given non-existing email, When findByEmail, Then should return empty")
        void givenNonExistingEmail_whenFindByEmail_thenReturnEmpty() {
            // Given & When
            Optional<User> foundUser = userRepository.findByEmail("non-existing@email.com");

            // Then
            assertThat(foundUser).isEmpty();

        }
    }


}
