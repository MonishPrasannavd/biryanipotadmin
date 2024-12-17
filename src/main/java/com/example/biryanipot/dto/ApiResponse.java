package com.example.biryanipot.dto;

public class ApiResponse {
    private boolean success;
    private String token;
    private String message;

    // Constructor
    private ApiResponse(boolean success, String token, String message) {
        this.success = success;
        this.token = token;
        this.message = message;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Static Builder
    public static ApiResponseBuilder builder() {
        return new ApiResponseBuilder();
    }

    // Builder Class
    public static class ApiResponseBuilder {
        private boolean success;
        private String token;
        private String message;

        public ApiResponseBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public ApiResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public ApiResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(success, token, message);
        }
    }
}
