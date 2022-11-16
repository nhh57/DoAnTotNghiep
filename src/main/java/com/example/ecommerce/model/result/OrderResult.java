package com.example.ecommerce.model.result;

import com.example.ecommerce.model.data.OrderDataModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResult {
    Integer totalPage;
    List<OrderDataModel> data;
}
