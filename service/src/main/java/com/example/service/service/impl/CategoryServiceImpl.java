package com.example.service.service.impl;

import com.example.service.dto.category.CategoryRequest;
import com.example.service.dto.category.CategoryResponse;
import com.example.service.dto.common.ApiResponse;
import com.example.service.entity.Category;
import com.example.service.exception.ResourceNotFoundException;
import com.example.service.repository.CategoryRepository;
import com.example.service.repository.ProductRepository;
import com.example.service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryResponse> getAllCategoríes() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        return toCategoryResponse(categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục có id " + id)));
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category newCategory = Category.builder()
                .name(categoryRequest.getName())
                .build();
        return toCategoryResponse(categoryRepository.save(newCategory));
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Integer id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục"));

        existing.setName(categoryRequest.getName());
        return toCategoryResponse(categoryRepository.save(existing));
    }

    @Override
    public void deleteCategory(Integer id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục"));
        categoryRepository.delete(existing);
    }

    // Chuyển Entity sang DTO
    private CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
