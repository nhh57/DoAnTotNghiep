package com.example.ecommerce.controller;

import com.example.ecommerce.model.BaseEntity;
import com.example.ecommerce.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<BaseEntity> getAllAccount (@RequestParam(name = "username") String username,
                                                     @RequestParam(name = "is_deleted") int isDeleted){
        BaseEntity response = new BaseEntity();

        return null;
    }
}
