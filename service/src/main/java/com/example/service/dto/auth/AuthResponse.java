package com.example.service.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String accessToken;
    private String username;
    private Date expireAt;
    private String refreshToken;
    private Date refreshTokenExpireAt;
}
