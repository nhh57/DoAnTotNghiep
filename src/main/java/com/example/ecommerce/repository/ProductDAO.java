package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE p.productName like %?1%")
    List<Product> findByName(String productName);
    @Query("SELECT p FROM Product p WHERE p.price > ?1 and p.price <?2")
    List<Product> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);

//    @Query("SELECT p FROM Product p ORDER BY p.numberOfSale DESC LIMIT ?1")
//    List<Product> findByBestSellingProducts(Integer numberOfProduct);

}
