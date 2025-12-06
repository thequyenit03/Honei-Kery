package com.example.service.service.impl;

import com.example.service.dto.auth.AuthResponse;
import com.example.service.dto.auth.LoginRequest;
import com.example.service.dto.auth.RegisterRequest;
import com.example.service.entity.Role;
import com.example.service.entity.User;
import com.example.service.exception.BadRequestException;
import com.example.service.repository.RoleRepository;
import com.example.service.repository.UserRepository;
import com.example.service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthResponse register(RegisterRequest request) {
        // 1. Check trùng username/email
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username đã tồn tại");
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email đã tồn tại");
        }

        // 2. Lấy role "ROLE_USER" (nếu chưa có thì tạo mới)
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseGet(() ->{
                    Role r = Role.builder().name("ROLE_USER").build();
                    return roleRepository.save(r);
                });

        // 3. Tạo user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .status("ACTIVE")
                .roles(roleUser)
                .build();
        user = userRepository.save(user);

        // 4. Tạo token "giả" (UUID)
        String token = UUID.randomUUID().toString();

        return AuthResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // 1. Tìm user theo username hoặc email
        User user = userRepository.findByUsername(request.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(request.getUsernameOrEmail()))
                .orElseThrow(() -> new BadRequestException("Username hoặc email không đúng !"));

        // 2. Check password
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu không đúng !");
        }

        // 3. Tạo token "giả"
        String token = UUID.randomUUID().toString();

        return AuthResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .token(token)
                .build();
    }
}
