package com.example.ecommerce.controller;

import com.example.ecommerce.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminController {
@Autowired
private AccountService accountService;


}
