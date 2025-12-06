package com.example.service.repository;

import com.example.service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    // Lấy tất cả sản phẩm đang active
    Page<Product> findByActiveTrue(Pageable pageable);

    // Lọc theo danh mục
    Page<Product> findByActiveTrueAndCategory_Id(Integer categoryId, Pageable pageable);

    // Tìm theo tên sản phẩm
    Page<Product> findByActiveTrueAndNameContainingIgnoreCase(Pageable pageable, String keyword);

    //Vừa lọc category vừa search theo tên sản phẩm
    Page<Product> findByActiveTrueAndCategory_IdAndNameContainingIgnoreCase(Pageable pageable, Integer catetoryId, String keyword);
}
