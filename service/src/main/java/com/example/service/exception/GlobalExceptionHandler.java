package com.example.service.exception;

import com.example.service.dto.common.ApiResponse;
import com.example.service.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Đánh dấu đây là nơi xử lý lỗi chung cho toàn bộ app
public class GlobalExceptionHandler {

    // 1. Xử lý lỗi Không tìm thấy (ResourceNotFoundException)
    // Ví dụ: Tìm sản phẩm ID 9999 không thấy -> vào đây
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        // Gọi hàm error của cậu nè
        ApiResponse<Object> errorResponse = ResponseUtils.error(
                String.valueOf(HttpStatus.NOT_FOUND.value()), // Mã lỗi "404"
                ex.getMessage() // Thông báo lỗi từ Service ném ra
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 2. Xử lý lỗi Dữ liệu đầu vào (BadRequestException)
    // Ví dụ: Đăng nhập sai pass, trùng email -> vào đây
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex) {
        ApiResponse<Object> errorResponse = ResponseUtils.error(
                String.valueOf(HttpStatus.BAD_REQUEST.value()), // Mã lỗi "400"
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 3. Xử lý lỗi liên quan đến Quyền (Forbidden/Unauthorized)
    // Cái này thường do Spring Security chặn, nhưng nếu cậu tự ném lỗi thì bắt ở đây
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleOtherExceptions(Exception ex) {
        // Lỗi không xác định (bug code, null pointer...) -> Trả về 500
        ApiResponse<Object> errorResponse = ResponseUtils.error(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), // Mã lỗi "500"
                "Lỗi hệ thống: " + ex.getMessage() // Hoặc ghi "Vui lòng thử lại sau" cho user đỡ sợ
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}