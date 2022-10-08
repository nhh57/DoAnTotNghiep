package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> findProductExist() {
        return productRepo.findProductExist();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepo.findById(id);
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepo.findByPrice(minPrice,maxPrice);
    }

    @Override
    public boolean existsById(Integer id) {
        return productRepo.existsById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepo.save(product);
    }

    @Override
    public List<Product> findByBestSellingProducts(Integer numberOfProduct) {
        return productRepo.findByBestSellingProducts(numberOfProduct);
    }


}
