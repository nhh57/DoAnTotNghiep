package com.example.ecommerce.model.data;

import java.math.BigDecimal;

public class CartDetailDataModel {
    private ProductDataModel product;
    private Integer amount;
    private BigDecimal moneyOfAProduct;

    public ProductDataModel getProduct() {
        return product;
    }

    public void setProduct(ProductDataModel product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getMoneyOfAProduct() {
        return moneyOfAProduct;
    }

    public void setMoneyOfAProduct() {
        double price = this.product.getPrice().doubleValue();
        this.moneyOfAProduct = new BigDecimal(price*this.amount);
    }
}
