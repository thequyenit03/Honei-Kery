package com.example.service.service;

import com.example.service.dto.auth.AuthResponse;
import com.example.service.dto.auth.LoginRequest;
import com.example.service.dto.auth.RegisterRequest;
import com.example.service.dto.auth.UserResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    UserResponse getProfile();
}
