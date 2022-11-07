package com.example.ecommerce.controller;


import com.example.ecommerce.model.data.CartDetailDataModel;
import com.example.ecommerce.model.data.OrderDataModel;
import com.example.ecommerce.model.result.CartResult;
import com.example.ecommerce.request.AccountRequest;
import com.example.ecommerce.response.BaseResponse;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/get-all")
    public ResponseEntity<List<OrderDataModel>> getAllOrder(){
        return ResponseEntity.ok(orderService.getAll());
    }

}
