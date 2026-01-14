package com.example.service.service.impl;

import com.example.service.dto.auth.*;
import com.example.service.entity.RefreshToken;
import com.example.service.entity.Role;
import com.example.service.entity.User;
import com.example.service.exception.BadRequestException;
import com.example.service.exception.ForbiddenException;
import com.example.service.exception.ResourceNotFoundException;
import com.example.service.repository.RefreshTokenRepository;
import com.example.service.repository.RoleRepository;
import com.example.service.repository.UserRepository;
import com.example.service.security.JwtUtils;
import com.example.service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository; // Inject thêm
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username đã tồn tại");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email đã tồn tại");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role User không tìm thấy"));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .roles(userRole)
                .status("ACTIVE")
                .build();

        userRepository.save(user);

        // Load UserDetails & Generate Tokens
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return generateAuthResponse(user, userDetails);
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User không tồn tại"));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return generateAuthResponse(user, userDetails);
    }

    @Override
    public UserResponse getProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User không tìm thấy"));
        return mapToUserResponse(user);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        // 1. Tìm Refresh Token trong DB
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new ResourceNotFoundException("Refresh Token không tồn tại trong DB!"));

        // 2. Kiểm tra hết hạn
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new ForbiddenException("Refresh token đã hết hạn. Vui lòng đăng nhập lại.");
        }

        // 3. Tạo Access Token mới
        User user = refreshToken.getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String newAccessToken = jwtUtils.generateToken(userDetails);

        // 4. Trả về
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .username(user.getUsername())
                .expireAt(jwtUtils.extractExpiration(newAccessToken))
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpireAt(Date.from(refreshToken.getExpiryDate()))
                .build();
    }

    // --- Helper Methods ---

    // Hàm dùng chung để tạo Access Token + Refresh Token và trả về Response
    private AuthResponse generateAuthResponse(User user, UserDetails userDetails) {
        String accessToken = jwtUtils.generateToken(userDetails);
        RefreshToken refreshToken = createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .username(user.getUsername())
                .expireAt(jwtUtils.extractExpiration(accessToken))
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpireAt(Date.from(refreshToken.getExpiryDate()))
                .build();
    }

    // Tạo Refresh Token và lưu vào DB
    private RefreshToken createRefreshToken(User user) {
        // Xóa token cũ của user nếu có (để mỗi user chỉ có 1 token active tại 1 thời điểm - tùy logic)
        // refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(jwtUtils.getRefreshExpiration()))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    private UserResponse mapToUserResponse(User user) {
        String roleName = (user.getRoles() != null) ? user.getRoles().getName() : "UNKNOWN";
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhone())
                .address(user.getAddress())
                .role(roleName)
                .status(user.getStatus())
                .build();
    }
}
