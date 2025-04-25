package com.example.e_commerce_backend.repositories;

import com.example.e_commerce_backend.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author yettaxue
 * @project e_commerce_backend
 * @date 24/4/2025
 */

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /***
     * @description find user info by email
     * @param email
     * @return java.util.Optional<com.example.e_commerce_backend.entities.User>
     * @author yettaxue
     * @date 24/4/2025
     */
    Optional<User> findByEmail(String email);
}
