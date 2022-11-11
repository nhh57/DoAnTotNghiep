package com.example.ecommerce.mvc.model;

import com.example.ecommerce.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCart extends Product {
    private int soLuong=1;

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
