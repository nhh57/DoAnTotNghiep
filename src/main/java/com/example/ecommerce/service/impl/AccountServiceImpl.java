package com.example.ecommerce.service.impl;

import com.example.ecommerce.common.Utils;
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
    public List<Account> getAllAccount(String keySearch, int isDeleted) throws Exception {
        return accountRepo.getAllAccount(keySearch,isDeleted);
    }

    @Override
    public void createAccount(Account account) throws Exception {
        accountRepo.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getOneAccount(String keySearch) throws Exception{
        return accountRepo.getAccount(keySearch);
    }
}
