package com.example.skillbridge.repositories;

import com.example.skillbridge.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Check if a category with the same name already exists
    boolean existsByName(String name);
}