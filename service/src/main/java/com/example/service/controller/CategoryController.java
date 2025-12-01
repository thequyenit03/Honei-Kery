package com.example.service.controller;

import com.example.service.entity.Category;
import com.example.service.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Integer id, @RequestBody Category category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return "Đã xóa category";
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Integer id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }
}
