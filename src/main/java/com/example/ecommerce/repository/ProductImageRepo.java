package com.example.ecommerce.repository;

import com.example.ecommerce.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImageRepo extends JpaRepository<ProductImage,Integer> {
    @Query("SELECT pi FROM ProductImage pi WHERE productId=?1")
    List<ProductImage> findByProductId(Integer productId);
}
