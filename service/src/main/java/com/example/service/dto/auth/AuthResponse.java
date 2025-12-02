package com.example.service.dto.auth;

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
    private Set<String> roles;
    private String token; //fake token
}
