package com.example.service.controller.client;

import com.example.service.dto.category.CategoryRequest;
import com.example.service.dto.category.CategoryResponse;
import com.example.service.dto.common.ApiResponse;
import com.example.service.service.CategoryService;
import com.example.service.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> listcategories = categoryService.getAllCategoríes();

        ApiResponse<List<CategoryResponse>> apiResponse = ResponseUtils.success(listcategories, "Thành công");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Integer id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);

        ApiResponse<CategoryResponse> apiResponse = ResponseUtils.success(categoryResponse, "Thành công");

        return ResponseEntity.ok(apiResponse);
    }

}
