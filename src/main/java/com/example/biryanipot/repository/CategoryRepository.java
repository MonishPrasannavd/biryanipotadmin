package com.example.biryanipot.repository;

import com.example.biryanipot.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Method to search categories by name (case insensitive)
    Page<Category> findByCategoryNameContainingIgnoreCase(String keyword, Pageable pageable);
}
