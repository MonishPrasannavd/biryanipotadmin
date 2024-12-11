package com.example.biryanipot.controller;

import com.example.biryanipot.entity.Blogs;
import com.example.biryanipot.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @Autowired
    private BlogsService blogsService;

    // Define the path where the image will be stored
    private final String UPLOAD_DIR = "uploads/";

    // Create a new blog
    @PostMapping
    public ResponseEntity<Blogs> createBlog(@RequestParam("blog") String blogJson, @RequestParam("image") MultipartFile image) {
        try {
            // Generate a unique file name to avoid name clashes
            String fileName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR, fileName);
            
            // Save the image to the server
            Files.createDirectories(path.getParent()); // Create directory if not exists
            image.transferTo(path); // Save the file

            // Create a blog entity and set image path
            Blogs blog = new Blogs();
            blog.setImagePath(path.toString());  // Set the image path in your entity
            
            // You can also set other blog details from the blogJson, for simplicity I'm skipping JSON parsing here
            // Example: blog.setBlogTitle(blogTitle);
            blog.setImageAltTag("Some alt text");
            blog.setMetaTitle("Meta Title");
            blog.setMetaDescription("Meta Description");

            // Save the blog and return the response
            Blogs createdBlog = blogsService.saveBlog(blog);
            return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Other methods remain the same
    @GetMapping
    public ResponseEntity<List<Blogs>> getAllBlogs() {
        List<Blogs> blogsList = blogsService.getAllBlogs();
        return new ResponseEntity<>(blogsList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blogs> getBlogById(@PathVariable("id") Long blogId) {
        Optional<Blogs> blog = blogsService.getBlogById(blogId);
        if (blog.isPresent()) {
            return new ResponseEntity<>(blog.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blogs> updateBlog(@PathVariable("id") Long blogId, @RequestBody Blogs blog) {
        Blogs updatedBlog = blogsService.updateBlog(blogId, blog);
        if (updatedBlog != null) {
            return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable("id") Long blogId) {
        blogsService.deleteBlog(blogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
