package com.example.ecommerce.repository;
import com.example.ecommerce.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesDAO extends JpaRepository<Categories,Integer> {
}
