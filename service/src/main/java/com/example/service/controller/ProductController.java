package com.example.service.controller;

import com.example.service.entity.Product;
import com.example.service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/{categoryId}")
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @PutMapping("/{id}/{categoryId}")
    public Product update(
            @PathVariable Integer id,
            @RequestBody Product product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Đã xóa product";
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }
}
