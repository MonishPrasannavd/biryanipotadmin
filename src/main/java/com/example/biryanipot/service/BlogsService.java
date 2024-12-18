package com.example.biryanipot.service;

import com.example.biryanipot.entity.Blogs;
import java.util.List;
import java.util.Optional;

public interface BlogsService {
    
    // Method to save a new blog
    Blogs saveBlog(Blogs blog);

    // Method to find a blog by its ID
    Optional<Blogs> getBlogById(Long blogId);

    // Method to get all blogs
    List<Blogs> getAllBlogs();

    // Method to delete a blog by its ID
    void deleteBlog(Long blogId);

    // Method to update an existing blog
    Blogs updateBlog(Long blogId, Blogs blog);
    
    List<Blogs> getBlogsByCategoryId(Long categoryId);
   }
