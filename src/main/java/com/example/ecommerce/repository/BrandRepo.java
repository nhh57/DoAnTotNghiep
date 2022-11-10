package com.example.ecommerce.repository;

import com.example.ecommerce.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepo extends JpaRepository<Brand,Integer> {
    @Query("SELECT b FROM Brand b WHERE b.isDeleted = 0")
    Page<Brand> findBrandExist(Pageable pageable);
    @Query("SELECT b FROM Brand b WHERE b.isDeleted = 0")
    List<Brand> findBrandExist();
}
