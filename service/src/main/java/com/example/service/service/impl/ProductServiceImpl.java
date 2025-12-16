package com.example.service.service.impl;

import com.example.service.dto.common.PagedResponse;
import com.example.service.dto.product.ProductCreateRequest;
import com.example.service.dto.product.ProductUpdateRequest;
import com.example.service.dto.product.ProductResponse;
import com.example.service.entity.Category;
import com.example.service.entity.Product;
import com.example.service.repository.CategoryRepository;
import com.example.service.repository.ProductRepository;
import com.example.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // -------------------- LIST + FILTER + PAGINATION --------------------

    @Override
    public PagedResponse<ProductResponse> getProducts(Integer page, Integer size, String sortBy,
                                                      String sortDir, Integer categoryId, String keyword) {

        int pageNumber = (page == null || page < 0) ? 0 : page;
        int pageSize = (size == null || size < 1) ? 6 : size;

        String sortField = (sortBy == null || sortBy.isEmpty()) ? "createdAt" : sortBy;
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField));

        Page<Product> productPage;

        boolean hasCategory = categoryId != null;
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();

        if (hasCategory && hasKeyword) {
            productPage = productRepository
                    .findByActiveTrueAndCategory_IdAndNameContainingIgnoreCase(
                            categoryId, keyword.trim(), pageable);
        } else if (hasCategory) {
            productPage = productRepository
                    .findByActiveTrueAndCategory_Id(categoryId, pageable);
        } else if (hasKeyword) {
            productPage = productRepository
                    .findByActiveTrueAndNameContainingIgnoreCase(keyword.trim(), pageable);
        } else {
            productPage = productRepository.findByActiveTrue(pageable);
        }

        List<ProductResponse> items = productPage
                .map(this::toProductResponse)
                .toList();

        return PagedResponse.<ProductResponse>builder()
                .items(items)
                .page(pageNumber)
                .size(pageSize)
                .totalItems(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .build();
    }

    // -------------------- GET --------------------

    @Override
    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return toProductResponse(product);
    }

    // -------------------- CREATE --------------------

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(BigDecimal.valueOf(request.getPrice()))
                .imageUrl(request.getImageUrl())
                .stock(request.getStock())
                .category(category)
                .active(true)
                .build();

        productRepository.save(product);

        return toProductResponse(product);
    }

    // -------------------- UPDATE --------------------

    @Override
    public ProductResponse updateProduct(Integer id, ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(BigDecimal.valueOf(request.getPrice()));
        product.setImageUrl(request.getImageUrl());
        product.setStock(request.getStock());
        product.setActive(request.getActive());
        product.setCategory(category);

        productRepository.save(product);

        return toProductResponse(product);
    }

    // -------------------- DELETE --------------------

    @Override
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    // -------------------- MAPPER --------------------

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .stock(product.getStock())
                .active(product.getActive())
                .category(product.getCategory())
                .build();
    }
}
