package com.example.service.service;

import com.example.service.dto.category.CategoryRequest;
import com.example.service.dto.category.CategoryResponse;
import com.example.service.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategoríes();
    CategoryResponse getCategoryById(Integer id);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(CategoryRequest categoryRequest, Integer id);
    void deleteCategory(Integer id);
}
