package com.example.service.service;

import com.example.service.dto.auth.*;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    UserResponse getProfile();
    AuthResponse refreshToken(RefreshTokenRequest request);
}
