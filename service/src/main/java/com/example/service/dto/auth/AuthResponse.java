package com.example.service.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

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
