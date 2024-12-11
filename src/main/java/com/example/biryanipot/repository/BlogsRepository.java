	package com.example.biryanipot.repository;

import com.example.biryanipot.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogsRepository extends JpaRepository<Blogs, Long> {
    // You can define custom query methods here if needed
}
