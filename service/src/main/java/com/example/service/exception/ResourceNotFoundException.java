package com.example.service.exception;

// 404 - không tìm thấy resource (user, product, order,...)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
