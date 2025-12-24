package com.example.service.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Nếu field nào = null thì Jackson sẽ KHÔNG đưa vào JSON
public class ApiResponse<T> {
    private LocalDateTime dateTime;
    private String errorCode;
    private String message;
    private T data;
    private boolean success;
}
