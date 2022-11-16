package com.example.ecommerce.controller;


import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.data.OrderDataModel;
import com.example.ecommerce.model.data.ProductDataModel;
import com.example.ecommerce.model.helper.OrderHelper;
import com.example.ecommerce.model.result.OrderResult;
import com.example.ecommerce.model.result.ProductResult;
import com.example.ecommerce.repository.OrderRepo;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepo orderRepo;

    OrderHelper orderHelper=new OrderHelper();

    @GetMapping("/get-all-admin/{page}/{size}")
    public ResponseEntity<OrderResult> getAllAdminOrder(@PathVariable("page") Integer page,
                                                   @PathVariable("size") Integer size){
        OrderResult orderResult=new OrderResult();
        List<OrderDataModel> list=orderService.findAllAdmin(page,size);
        orderResult.setData(list);
        orderResult.setTotalPage(Utils.getTotalPage(size,orderRepo.findAll().size()));
        return ResponseEntity.ok(orderResult);
    }
    @GetMapping("/get-all/{page}/{size}")
    public ResponseEntity<OrderResult> getAllOrder(@PathVariable("page") Integer page,
                                                   @PathVariable("size") Integer size){
        OrderResult orderResult=new OrderResult();
        List<OrderDataModel> list=orderService.findOrdersExist(page,size);
        orderResult.setData(list);
        orderResult.setTotalPage(Utils.getTotalPage(size,orderRepo.findOrdersExist().size()));
        return ResponseEntity.ok(orderResult);
    }

    @PostMapping("/crud/save")
    public ResponseEntity<OrderDataModel> save(@RequestBody OrderDataModel orderDataModel) {
        return ResponseEntity.ok(orderService.save(orderDataModel));
    }

    @PostMapping("/crud/delete/{id}")
    public ResponseEntity<OrderDataModel> delete(@PathVariable("id") Integer id) {
        OrderDataModel orderDataModel= orderService.findById(id);
        if (!orderService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderDataModel.setIsDeleted(true);
        orderService.save(orderDataModel);
        return ResponseEntity.ok(orderDataModel);
    }

}
