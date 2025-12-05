package com.example.service.exception;

// 403 - đã đăng nhập nhưng không đủ quyền
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
