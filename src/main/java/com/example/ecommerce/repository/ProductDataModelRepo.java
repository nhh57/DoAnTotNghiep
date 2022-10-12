package com.example.ecommerce.repository;

import com.example.ecommerce.model.data.ProductDataModelCreate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
@Repository("ProductDataModelRepo")
public interface ProductDataModelRepo extends JpaRepository<ProductDataModelCreate,Integer> {
    @Modifying(clearAutomatically = true)
    @Query(value = "{CALL sp_c_create_product(:productName, :price, :discount, :note, :images, :numberOfSale, :category, :brand)}", nativeQuery = true)
    void createProduct(@Param("productName") String product_name,
                       @Param("price") BigDecimal price,
                       @Param("discount") Double discount,
                       @Param("note") String note,
                       @Param("images") String images,
                       @Param("numberOfSale") int number_of_sale,
                       @Param("category") String category,
                       @Param("brand") String brand) ;
}
