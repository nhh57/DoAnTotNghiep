package com.example.ecommerce.mvc.model;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdminResult {
    Product product;
    List<ProductImage> listProductImage;
}
