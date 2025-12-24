package com.example.service.repository;

import com.example.service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    // Lấy tất cả sản phẩm đang active
    Page<Product> findByActiveTrue(Pageable pageable);

    // Lọc theo danh mục
    Page<Product> findByActiveTrueAndCategory_Id(Integer categoryId, Pageable pageable);

    // Tìm theo tên sản phẩm
    Page<Product> findByActiveTrueAndNameContainingIgnoreCase(String keyword, Pageable pageable);

    //Vừa lọc category vừa search theo tên sản phẩm
    Page<Product> findByActiveTrueAndCategory_IdAndNameContainingIgnoreCase(Integer catetoryId, String keyword, Pageable pageable);

    // Tìm sản phẩm theo id và trạng thái true
    Optional<Product> findByIdAndActiveTrue(Integer id);
}
