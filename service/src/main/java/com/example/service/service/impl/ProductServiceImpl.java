package com.example.service.service.impl;

import com.example.service.dto.common.PagedResponse;
import com.example.service.entity.Category;
import com.example.service.entity.Product;
import com.example.service.exception.ResourceNotFoundException;
import com.example.service.repository.CategoryRepository;
import com.example.service.repository.ProductRepository;
import com.example.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public PagedResponse<Product> getProducts(Integer page, Integer size, String sortBy, String sortDir, Integer categoryId, String keyword) {
        int pageNumber = (page == null || page < 0) ? 0 : page;
        int pageSize = (size == null || size < 0) ? 6 : size;
        String sortField = (sortBy == null || sortBy.isEmpty()) ? "createdAt" : sortBy;
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField));

        Page<Product> productPage;
        boolean hasCategory = categoryId != null;
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();

        if(hasCategory && hasKeyword){
            productPage = productRepository
                    .findByActiveTrueAndCategory_IdAndNameContainingIgnoreCase(
                            pageable, categoryId, keyword.trim());
        } else if (hasCategory) {
            productPage = productRepository
                    .findByActiveTrueAndCategory_Id(categoryId, pageable);
        } else if (hasKeyword) {
            productPage = productRepository
                    .findByActiveTrueAndNameContainingIgnoreCase(pageable, keyword.trim());
        } else {
            productPage = productRepository
                    .findByActiveTrue(pageable);
        }

        List<Product> items = productPage.getContent();

        return PagedResponse.<Product>builder()
                .items(items)
                .page(pageNumber)
                .size(pageSize)
                .totalItems(productPage.getTotalPages())
                .totalPages(productPage.getTotalPages())
                .build();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm"));
    }

    @Override
    public Product createProduct(Product product) {
        Integer categoryId = product.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));

        product.setId(null);
        product.setCategory(category);
        product.setActive(true);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer id, Product product) {
        Product existing = getProductById(id);

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setImageUrl(product.getImageUrl());
        existing.setStock(product.getStock());
        existing.setActive(product.getActive());

        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Integer categoryId = product.getCategory().getId();
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
            existing.setCategory(category);
        }

        return productRepository.save(existing);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product existing = getProductById(id);
        productRepository.delete(existing);
    }
}
