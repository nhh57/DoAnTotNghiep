package com.example.ecommerce.model.data;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.math.BigDecimal;


public class CartDetailDataModel {
    private Integer cartId;
    private Integer productId;
    private Integer amount;
    private ProductDataModel productDataModel;


    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    public ProductDataModel getProductDataModel() {
        return productDataModel;
    }

    public void setProductDataModel(ProductDataModel productDataModel) {
        this.productDataModel = productDataModel;
    }
}
