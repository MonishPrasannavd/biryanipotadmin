package com.example.biryanipot.impl;

import com.example.biryanipot.entity.Blogs;
import com.example.biryanipot.repository.BlogsRepository;
import com.example.biryanipot.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogsServiceImpl implements BlogsService {

    private final BlogsRepository blogsRepository;

    @Autowired
    public BlogsServiceImpl(BlogsRepository blogsRepository) {
        this.blogsRepository = blogsRepository;
    }

    @Override
    public Blogs saveBlog(Blogs blog) {
        return blogsRepository.save(blog);
    }

    @Override
    public Optional<Blogs> getBlogById(Long blogId) {
        return blogsRepository.findById(blogId);
    }

    @Override
    public List<Blogs> getAllBlogs() {
        return blogsRepository.findAll();
    }

    @Override
    public void deleteBlog(Long blogId) {
        blogsRepository.deleteById(blogId);
    }

    @Override
    public Blogs updateBlog(Long blogId, Blogs blog) {
        if (blogsRepository.existsById(blogId)) {
            blog.setBlogId(blogId);
            return blogsRepository.save(blog);
        } else {
            throw new IllegalArgumentException("Blog with ID " + blogId + " not found.");
        }
    }
}

