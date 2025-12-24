package com.example.service.controller;

import com.cloudinary.Api;
import com.example.service.dto.common.ApiResponse;
import com.example.service.dto.common.PagedResponse;
import com.example.service.dto.product.ProductCreateRequest;
import com.example.service.dto.product.ProductUpdateRequest;
import com.example.service.dto.product.ProductResponse;
import com.example.service.service.ProductService;
import com.example.service.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> getProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword
    ) {
        PagedResponse<ProductResponse> productResponse = productService.getProducts(page, size, sortBy, sortDir, categoryId, keyword);

        ApiResponse<PagedResponse<ProductResponse>> apiResponse = ResponseUtils.success(productResponse, "Thành công");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Integer id) {
        ProductResponse productResponse = productService.getProductById(id);

        ApiResponse<ProductResponse> apiResponse = ResponseUtils.success(productResponse, "Thành công");

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestBody ProductCreateRequest request
    ) {
        ProductResponse response = productService.createProduct(request);

        ApiResponse<ProductResponse> apiResponse = ResponseUtils.success(response, "Tạo sản phẩm thành công");

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Integer id,
            @RequestBody ProductUpdateRequest request
    ) {
        ProductResponse response = productService.updateProduct(id, request);

        ApiResponse<ProductResponse> apiResponse = ResponseUtils.success(response, "Cập nhật sản phẩm thành công");

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);

        ApiResponse<Void> apiResponse = ResponseUtils.success(null, "Xóa sản phẩm thành công");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
