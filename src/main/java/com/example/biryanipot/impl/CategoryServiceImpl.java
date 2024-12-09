package com.example.biryanipot.impl;

import com.example.biryanipot.entity.Category;
import com.example.biryanipot.exception.ResourceNotFoundException;
import com.example.biryanipot.repository.CategoryRepository;
import com.example.biryanipot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> getAllCategories(int page, int size, String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return categoryRepository.findByCategoryNameContainingIgnoreCase(keyword, PageRequest.of(page, size));
        }
        return categoryRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
