package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.AccountOauthDataModel;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE p.productName like %?1%")
    List<Product> findByName(String productName);
    @Query("SELECT p FROM Product p WHERE p.price > ?1 and p.price <?2")
    List<Product> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    @Query(value = "SELECT p FROM Product p ORDER BY p.numberOfSale DESC LIMIT ?1", nativeQuery = true)
    List<Product> findByBestSellingProducts(Integer numberOfProduct);
    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0")
    List<Product> findProductExist();

    Product findByProductName(String productName);
}
