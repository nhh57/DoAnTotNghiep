package com.example.ecommerce.controller;


import com.example.ecommerce.request.AccountRequest;
import com.example.ecommerce.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> createAccount(@Valid @RequestBody AccountRequest request) throws Exception {
        BaseResponse response = new BaseResponse(); 

        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }
}
