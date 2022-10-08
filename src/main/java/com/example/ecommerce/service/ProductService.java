package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    List<Product> findProductExist();
    Optional<Product> findById(Integer id);
    List<Product> findByName(String name);
    List<Product> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    boolean existsById(Integer id);
    Product save(Product product);
    List<Product> findByBestSellingProducts(Integer numberOfProduct);


}
