package com.example.ecommerce.model.data;

import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDetailDataModel {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer amount;
    private BigDecimal price;
    private Boolean isDeleted;
    private ProductDataModel products;
}
