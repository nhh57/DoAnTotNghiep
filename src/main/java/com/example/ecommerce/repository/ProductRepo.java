package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;


public interface ProductRepo extends JpaRepository<Product,Integer> {
//    @Query(value="INSERT INTO " +
//            " Product(product_name,price,discount,note,images,number_of_sale,category_id,brand_id,is_deleted) " +
//            " VALUES(?1,?2,?3,?4,?5,?6,?7,?8,?9)",nativeQuery = true)
//    Product insert(String productName,BigDecimal price,Integer discount,String note,String images,
//                   Integer numberOfSale,Integer categoryID,Integer brandId,Boolean isDeleted);
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
