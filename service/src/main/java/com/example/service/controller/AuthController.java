package com.example.service.controller;

import com.example.service.dto.auth.AuthResponse;
import com.example.service.dto.auth.LoginRequest;
import com.example.service.dto.auth.RegisterRequest;
import com.example.service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // với kiểu token stateless (UUID/JWT) thì logout chủ yếu là phía FE xoá token
        // ở đây backend chỉ trả về message cho đúng flow
        return ResponseEntity.ok(Map.of(
                "message", "Đăng xuất thành công" //Xóa token phía client
        ));
    }
}
