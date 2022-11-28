package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;


public interface ProductRepo extends JpaRepository<Product,Integer> {
//    @Query(value="INSERT INTO " +
//            " Product(product_name,price,discount,note,images,number_of_sale,category_id,brand_id,is_deleted) " +
//            " VALUES(?1,?2,?3,?4,?5,?6,?7,?8,?9)",nativeQuery = true)
//    Product insert(String productName,BigDecimal price,Integer discount,String note,String images,
//                   Integer numberOfSale,Integer categoryID,Integer brandId,Boolean isDeleted);
    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0 AND p.productName like %?1%")
    List<Product> findByName(String productName);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0 AND p.productName like %?1%")
    Page<Product> findByName(Pageable pageable, String productName);
    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0 AND p.price > ?1 and p.price <?2")
    List<Product> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    @Query(value = "SELECT * FROM product p ORDER BY p.number_of_sale DESC LIMIT ?1", nativeQuery = true)
    List<Product> findByBestSellingProducts(Integer numberOfProduct);
    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0")
    Page<Product> findProductExist(Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0")
    List<Product> findProductExist();

    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0 AND p.brandId=?1")
    List<Product> findByBrandId(Integer brandId);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0 AND p.brandId=?1")
    Page<Product> findByBrandId(Pageable pageable,Integer brandId);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0 AND p.categoryId=?1")
    List<Product> findByCategoryId(Integer categoryId);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0 AND p.categoryId=?1")
    Page<Product> findByCategoryId(Pageable pageable,Integer categoryId);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0 AND p.brandId=?1 AND p.categoryId=?2")
    List<Product> findByBrandIdAndCategoryId(Integer brandId,Integer categoryId);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = 0 AND p.brandId=?1 AND p.categoryId=?2")
    Page<Product> findByBrandIdAndCategoryId(Pageable pageable,Integer brandId,Integer categoryId);

}
