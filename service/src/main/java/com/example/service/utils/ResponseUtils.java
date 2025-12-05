package com.example.service.utils;

import com.example.service.dto.ApiResponse;

import java.time.LocalDateTime;

/*
* Class tiện tích để tạo ApiResponse nhanh gọn
* */
public class ResponseUtils {

    /**
     * Tạo response thành công.
     * @param data    dữ liệu trả về (có thể là user, product, list,...)
     * @param message message hiển thị cho FE (vd: "Đăng ký thành công")
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .dateTime(LocalDateTime.now())
                .errorCode("200")
                .message(message)
                .data(data)
                .success(true)
                .build();
    }

    /**
     * Tạo response lỗi.
     * @param errorCode vd: "400", "401", "500"
     * @param message   mô tả lỗi
     */
    public static <T> ApiResponse<T> error(String errorCode, String message) {
        return ApiResponse.<T>builder()
                .dateTime(LocalDateTime.now())
                .errorCode(errorCode)
                .message(message)
                .success(false)
                .build();
    }
}
