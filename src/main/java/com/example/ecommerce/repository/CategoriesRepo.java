package com.example.ecommerce.repository;
import com.example.ecommerce.model.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepo extends JpaRepository<Categories,Integer> {
    @Query("SELECT c FROM Categories c WHERE c.isDeleted = 0")
    Page<Categories> findCategoriesExist(Pageable pageable);
    @Query("SELECT c FROM Categories c WHERE c.isDeleted = 0")
    List<Categories> findCategoriesExist();

    @Query("SELECT c FROM Categories c WHERE c.categoryName like %?1%")
    List<Categories> findByCategoryName(String categoryName);

    @Query("SELECT c FROM Categories c WHERE c.categoryName like %?1%")
    Page<Categories> findByCategoryNamePage(Pageable pageable, String categoryName);
}
