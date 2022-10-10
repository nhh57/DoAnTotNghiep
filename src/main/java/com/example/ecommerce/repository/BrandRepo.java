package com.example.ecommerce.repository;

import com.example.ecommerce.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepo extends JpaRepository<Brand,Integer> {
}
