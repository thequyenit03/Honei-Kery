package com.example.service.exception;

import com.example.service.dto.ApiResponse;
import com.example.service.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Lớp này sẽ nghe tất cả exception trong project
 * và convert chúng sang ApiResponse để trả về cho FE.
 *
 * ControllerAdvice giúp bắt TẤT CẢ exception trong ứng dụng,
 * đảm bảo mọi lỗi đều trả về JSON format chuẩn ApiResponse.
 */
@Slf4j
@ControllerAdvice //annotation này đánh dấu đây là lớp bắt lỗi toàn cục
public class GlobalExceptionHandler {
    // ============================================
    // 400 – Bad Request (dữ liệu sai, validate fail)
    // ============================================
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex) {
        log.warn("400 BadRequest: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseUtils.error("400", ex.getMessage()));
    }

    // ============================================
    // 401 – Unauthorized (chưa đăng nhập / token sai)
    // ============================================
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<?>> handleUnauthorized(UnauthorizedException ex) {
        log.warn("401 Unauthorized: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseUtils.error("401", ex.getMessage()));
    }

    // ============================================
    // 403 – Forbidden (đã login nhưng không đủ quyền)
    // ============================================
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<?>> handleForbidden(ForbiddenException ex) {
        log.warn("403 Forbidden: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ResponseUtils.error("403", ex.getMessage()));
    }

    // ============================================
    // 404 – Not Found (không tìm thấy dữ liệu)
    // ============================================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(ResourceNotFoundException ex) {
        log.warn("404 Not Found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseUtils.error("404", ex.getMessage()));
    }

    // ============================================
    // 502 – Bad Gateway (gọi dịch vụ khác bị lỗi)
    // ============================================
    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<ApiResponse<?>> handleBadGateway(BadGatewayException ex) {
        log.error("502 BadGateway: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ResponseUtils.error("502", ex.getMessage()));
    }

    // ============================================
    // 503 – Service Unavailable (service phụ trợ down / quá tải)
    // ============================================
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ApiResponse<?>> handleServiceUnavailable(ServiceUnavailableException ex) {
        log.error("503 ServiceUnavailable: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ResponseUtils.error("503", ex.getMessage()));
    }

    // ============================================
    // 500 – Internal Server Error (bắt tất cả lỗi còn lại)
    // LƯỚI CUỐI CÙNG
    // ============================================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAll(Exception ex) {
        // Log đầy đủ stacktrace để dev debug
        log.error("500 InternalServerError", ex);

        ApiResponse<?> body = ResponseUtils.error(
                "500",
                "Lỗi hệ thống, vui lòng thử lại sau"
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}
