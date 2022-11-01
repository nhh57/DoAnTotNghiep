package com.example.ecommerce.request;

import java.math.BigDecimal;

public class ProductWarehouseRequest {
    private String nameProduct;
    private int statusType;
    private BigDecimal importPirce;
    private BigDecimal totalPrice;
    private String note;
    private int amount;

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getStatusType() {
        return statusType;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

    public BigDecimal getImportPirce() {
        return importPirce;
    }

    public void setImportPirce(BigDecimal importPirce) {
        this.importPirce = importPirce;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
