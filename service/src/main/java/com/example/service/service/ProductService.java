package com.example.service.service;

import com.example.service.entity.Category;
import com.example.service.entity.Product;
import com.example.service.repository.CategoryRepository;
import com.example.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product update(Integer id, Product product);
    void delete(Integer id);
    Product getById(Integer id);
    List<Product> getAll();
}

@Service
@RequiredArgsConstructor
class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    @Override
    public Product getById(Integer id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product không tồn tại"));
    }
    @Override
    public Product create(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product update(Integer id, Product newData) {
        Product product = getById(id);

        product.setName(newData.getName());
        product.setDescription(newData.getDescription());
        product.setPrice(newData.getPrice());
        product.setStock(newData.getStock());
        product.setCategory(newData.getCategory());

        return productRepo.save(product);
    }

    @Override
    public void delete(Integer id) {
        productRepo.deleteById(id);
    }



    @Override
    public List<Product> getAll() {
        return productRepo.findAll();
    }
}