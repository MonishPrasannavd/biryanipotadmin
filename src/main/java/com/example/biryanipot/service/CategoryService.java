package com.example.biryanipot.service;

import com.example.biryanipot.entity.Category;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CategoryService {

    Page<Category> getAllCategories(int page, int size, String keyword);

    Category saveCategory(Category category);

    Optional<Category> getCategoryById(Long id);

    void deleteCategory(Long id);
}
