package com.example.service.dto.product;

import com.example.service.dto.category.CategoryResponse;
import com.example.service.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductResponse {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer stock;
    private boolean active;
    private CategoryResponse category;
}
