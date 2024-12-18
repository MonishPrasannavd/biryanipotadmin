package com.example.biryanipot.controller;

import com.example.biryanipot.entity.Blogs;
import com.example.biryanipot.service.BlogsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/blogs")
public class BlogsController {

    @Value("${upload.dir}")
    private String uploadDir; // Inject the upload directory path from application.properties

    @Autowired
    private BlogsService blogsService;

    // Helper method to validate image file type and size
    private boolean isValidImage(MultipartFile image) {
        // Validate file type
        String contentType = image.getContentType();
        if (contentType == null || 
            !(contentType.equals("image/png") || 
              contentType.equals("image/jpeg") || 
              contentType.equals("image/webp") || 
              contentType.equals("image/svg+xml"))) {
            return false; // Invalid file type
        }

        // Validate file size (150KB to 200KB)
        long fileSizeInKB = image.getSize() / 1024;
        return fileSizeInKB >= 150 && fileSizeInKB <= 200; // File size must be in range
    }

    // Create a new blog
    @PostMapping
    public ResponseEntity<Blogs> createBlog(@RequestParam("blog") String blogJson, @RequestParam("image") MultipartFile image) {
        try {
            // Validate the image file
            if (!isValidImage(image)) {
                return ResponseEntity.badRequest()
                        .body(null); // Invalid image type or size
            }

            // Generate a unique file name to avoid name clashes
            String fileName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);

            // Save the image to the server
            Files.createDirectories(path.getParent()); // Create directory if not exists
            image.transferTo(path); // Save the file

            // Parse the blogJson into a Blog object
            ObjectMapper objectMapper = new ObjectMapper();
            Blogs blog = objectMapper.readValue(blogJson, Blogs.class); // Convert JSON to Blogs object

            // Set the image path in the blog entity
            blog.setImagePath(path.toString());

            // Save the blog and return the response
            Blogs createdBlog = blogsService.saveBlog(blog);
            return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace(); // Log the error (use a logging framework in production)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Generic server error
        }
    }

    // Get all blogs
    @GetMapping
    public ResponseEntity<List<Blogs>> getAllBlogs() {
        List<Blogs> blogsList = blogsService.getAllBlogs();
        return new ResponseEntity<>(blogsList, HttpStatus.OK);
    }

    // Get a blog by ID
    @GetMapping("/{id}")
    public ResponseEntity<Blogs> getBlogById(@PathVariable("id") Long blogId) {
        Optional<Blogs> blog = blogsService.getBlogById(blogId);
        return blog.map(b -> new ResponseEntity<>(b, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update a blog by ID
    @PutMapping("/{id}")
    public ResponseEntity<Blogs> updateBlog(@PathVariable("id") Long blogId, @RequestBody Blogs blog) {
        try {
            Blogs updatedBlog = blogsService.updateBlog(blogId, blog);
            return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a blog by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable("id") Long blogId) {
        blogsService.deleteBlog(blogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get blogs by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Blogs>> getBlogsByCategory(@PathVariable("categoryId") Long categoryId) {
        List<Blogs> blogs = blogsService.getBlogsByCategoryId(categoryId);
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 if no blogs are found
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }
}
