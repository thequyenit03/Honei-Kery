package com.example.service.service;

import com.example.service.dto.common.PagedResponse;
import com.example.service.dto.product.ProductCreateRequest;
import com.example.service.dto.product.ProductResponse;
import com.example.service.dto.product.ProductUpdateRequest;
import com.example.service.entity.Product;

public interface ProductService {
    PagedResponse<ProductResponse> getProducts(
            Integer page,
            Integer size,
            String sortBy,
            String sortDir,
            Integer categoryId,
            String keyword
    );

    ProductResponse getProductById(Integer id);

    ProductResponse getActiveProductById(Integer id);

    ProductResponse createProduct(ProductCreateRequest request);

    ProductResponse updateProduct(Integer id, ProductUpdateRequest request);

    void deleteProduct(Integer id);
}
