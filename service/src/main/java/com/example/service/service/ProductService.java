package com.example.service.service;

import com.example.service.dto.common.PagedResponse;
import com.example.service.entity.Product;

public interface ProductService {
    PagedResponse<Product> getProducts(
            Integer page,
            Integer size,
            String sortBy,
            String sortDir,
            Integer categoryId,
            String keyword
    );

    Product getProductById(Integer id);
    Product createProduct(Product product);
    Product updateProduct(Integer id, Product product);
    void deleteProduct(Integer id);
}
