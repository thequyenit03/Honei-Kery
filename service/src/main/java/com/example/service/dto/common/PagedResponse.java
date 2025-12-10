package com.example.service.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> items; // danh sách dữ liệu (ví dụ: list Product)
    private int page; // số trang hiện tại
    private int size; // số phần tử mỗi trang
    private long totalItems; // tổng số bản ghi trong DB
    private int totalPages; // tổng số trang
}
