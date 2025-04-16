package com.example.skillbridge.services;

import com.example.skillbridge.exceptions.ResourceNotFoundException;
import com.example.skillbridge.models.Category;
import com.example.skillbridge.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        // Prevent duplicate categories
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category '" + category.getName() + "' already exists!");
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
    }

    @Override
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(updatedCategory.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        categoryRepository.delete(category);
    }
}