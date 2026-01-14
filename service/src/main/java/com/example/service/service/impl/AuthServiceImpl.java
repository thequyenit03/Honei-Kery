package com.example.service.service.impl;

import com.example.service.dto.auth.AuthResponse;
import com.example.service.dto.auth.LoginRequest;
import com.example.service.dto.auth.RegisterRequest;
import com.example.service.dto.auth.UserResponse;
import com.example.service.entity.Role;
import com.example.service.entity.User;
import com.example.service.exception.BadRequestException;
import com.example.service.exception.ResourceNotFoundException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        // 1. Kiểm tra thông tin đầu vào
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username đã tồn tại");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email đã tồn tại");
        }

        // 2. Lấy Role mặc định (USER)
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role User không tìm thấy"));

        // 3. Tạo User Entity
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .roles(userRole)
                .status(true)
                .build();

        // 4. Lưu vào DB
        userRepository.save(user);

        // 5. Tạo Token (User entity cần implement UserDetails để hàm này hoạt động tốt nhất)
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String jwtToken = jwtUtils.generateToken(userDetails);

        // 6. Trả về Response kèm Token và User Info
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .user(mapToUserResponse(user))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // 1. Xác thực Username/Password thông qua AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Tìm User từ DB để lấy đầy đủ thông tin (Entity)
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User không tồn tại"));

        // 3. Tạo Token từ User đã xác thực
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateToken(userDetails);

        // 4. Trả về Token và User Info
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .user(mapToUserResponse(user))
                .build();
    }

    @Override
    public UserResponse getProfile() {
        // Lấy username người dùng hiện tại từ SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Truy vấn DB để lấy thông tin mới nhất
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User không tìm thấy"));

        return mapToUserResponse(user);
    }

    // Hàm tiện ích để chuyển đổi từ User Entity sang UserResponse DTO
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
