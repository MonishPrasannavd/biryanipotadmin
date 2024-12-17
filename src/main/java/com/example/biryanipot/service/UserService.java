package com.example.biryanipot.service;

import com.example.biryanipot.dto.RegisterRequest;
import com.example.biryanipot.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User registerUser(RegisterRequest registerRequest);
}