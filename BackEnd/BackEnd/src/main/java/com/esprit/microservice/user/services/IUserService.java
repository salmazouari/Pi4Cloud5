package com.esprit.microservice.user.services;

import com.esprit.microservice.user.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    // CRUD operations
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);

    // Authentication operations
    User register(User user);
    User login(String username, String password);
}
