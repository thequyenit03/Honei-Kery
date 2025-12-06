package com.example.service.dto.auth;

import com.example.service.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class AuthResponse {
    private Integer id;
    private String username;
    private String email;
    private Role roles;
    private String token; //fake token
}
