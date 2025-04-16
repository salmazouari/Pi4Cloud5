package com.example.skillbridge.services;

import com.example.skillbridge.models.Category;
import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Long categoryId);
    Category updateCategory(Long categoryId, Category category);
    void deleteCategory(Long categoryId);
}