package com.example.ecommerce.model.result;

import com.example.ecommerce.model.data.CartDetailDataModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResult {
    private Integer numberOfCart;
    private Integer totalMoney;
    private List<CartDetailDataModel> listCartDetail;
}
