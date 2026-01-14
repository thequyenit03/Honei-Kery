package com.example.service.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("user")
    private UserResponse user;
}
