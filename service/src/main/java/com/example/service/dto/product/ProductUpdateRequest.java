package com.example.service.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer stock;
    private Boolean active;
    private Integer categoryId;
}
