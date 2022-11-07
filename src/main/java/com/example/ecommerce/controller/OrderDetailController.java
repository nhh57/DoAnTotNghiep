package com.example.ecommerce.controller;

import com.example.ecommerce.model.data.OrderDataModel;
import com.example.ecommerce.model.data.OrderDetailDataModel;
import com.example.ecommerce.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;
    @GetMapping("/get-all/{orderId}")
    public ResponseEntity<List<OrderDetailDataModel>> getAllOrderDetail(@PathVariable("orderId") Integer orderId){
        return ResponseEntity.ok(orderDetailService.getAll(orderId));
    }
}
