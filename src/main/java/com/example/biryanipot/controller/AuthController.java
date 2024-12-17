package com.example.biryanipot.controller;

import com.example.biryanipot.dto.ApiResponse;
import com.example.biryanipot.dto.LoginRequest;
import com.example.biryanipot.dto.RegisterRequest;
import com.example.biryanipot.service.JwtService;
import com.example.biryanipot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Load user details
            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());

            // Generate JWT token
            String token = jwtService.generateToken(userDetails);

            // Build and return the API response
            ApiResponse response = ApiResponse.builder()
                    .success(true)
                    .token(token)
                    .message("Login successful")
                    .build();

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("Invalid username or password")
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .message("An error occurred")
                            .build()
            );
        }
    }

    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Register the user
            userService.registerUser(registerRequest);

            // Use builder pattern to create the response
            ApiResponse response = ApiResponse.builder()
                    .success(true)
                    .message("User registered successfully")
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Use builder pattern to create the error response
            ApiResponse response = ApiResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(400).body(response);
        }
    }
}
