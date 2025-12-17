package com.example.service.controller.admin;

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
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
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

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);

        ApiResponse<CategoryResponse> apiResponse = ResponseUtils.success(categoryResponse, "Tạo danh mục thành công");

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Integer id) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryRequest, id);

        ApiResponse<CategoryResponse> apiResponse = ResponseUtils.success(categoryResponse, "Cập nhật danh mục thành công");

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);

        ApiResponse<Void> apiResponse = ResponseUtils.success(null, "Xóa danh mục thành công");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
