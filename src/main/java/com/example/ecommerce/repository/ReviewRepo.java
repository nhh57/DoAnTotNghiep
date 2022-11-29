package com.example.ecommerce.repository;

import com.example.ecommerce.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Reviews,Integer> {

    List<Reviews> findByProductId(int productId);
}
