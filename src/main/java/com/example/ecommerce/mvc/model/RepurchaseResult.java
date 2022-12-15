package com.example.ecommerce.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepurchaseResult {
    List<CartDetailResult> listCartDetail;
    Integer numberOfCart;
}
