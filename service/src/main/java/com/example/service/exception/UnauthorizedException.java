package com.example.service.exception;

// 401 - chưa đăng nhập hoặc token sai
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
