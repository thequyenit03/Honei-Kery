package com.example.service.service;

import com.example.service.entity.Category;
import com.example.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    Category update(Integer id, Category category);
    void delete(Integer id);
    Category getById(Integer id);
    List<Category> getAll();
}

@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Integer id, Category newData) {
        Category category = getById(id);
        category.setName(newData.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
