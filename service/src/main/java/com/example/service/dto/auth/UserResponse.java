package com.example.service.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String role;
    private boolean status;
}
