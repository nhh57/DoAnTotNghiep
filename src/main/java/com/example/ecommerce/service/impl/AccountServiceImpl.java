package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.repository.AccountRepo;
import com.example.ecommerce.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepo accountRepo;
    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccount(String username, int isDeleted) throws Exception {

        return null;
    }
}
