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
    @JsonProperty("token") // Access Token
    private String accessToken;

    private String username;

    @JsonProperty("expire_at")
    private Date expireAt;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("refresh_token_expire_at")
    private Date refreshTokenExpireAt;
}
