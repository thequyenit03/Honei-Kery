package com.example.service.controller;

import com.cloudinary.Api;
import com.example.service.dto.auth.*;
import com.example.service.dto.common.ApiResponse;
import com.example.service.service.AuthService;
import com.example.service.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        // 1. Gọi service để xử lý logic
        AuthResponse authResponse = authService.register(request);

        // 2. Dùng ResponseUtils.success để wrap vào format chung
        ApiResponse<AuthResponse> apiResponse = ResponseUtils.success(authResponse, "Đăng ký thành công");

        // 3. Trả về cho FE
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        ApiResponse<AuthResponse> apiResponse = ResponseUtils.success(authResponse, "Đăng nhập thành công");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        AuthResponse authResponse = authService.refreshToken(refreshTokenRequest);
        ApiResponse<AuthResponse> apiResponse = ResponseUtils.success(authResponse, "Làm mới token thành công");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> getUserProfile(){
        UserResponse profile = authService.getProfile();
        ApiResponse<UserResponse> apiResponse = ResponseUtils.success(profile, "Thành công");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        // ở đây backend chỉ trả về message cho đúng flow
        ApiResponse<String> apiResponse = ResponseUtils.success(null, "Đăng xuất thành công");

        return ResponseEntity.ok(apiResponse);
    }
}
