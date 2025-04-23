package com.example.skillbridge.services;

import com.example.skillbridge.models.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // CRUD operations
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);

    // Authentication operations
    User register(User user);
    User login(String username, String password);
    User getCurrentUser(org.springframework.security.core.Authentication authentication);
}