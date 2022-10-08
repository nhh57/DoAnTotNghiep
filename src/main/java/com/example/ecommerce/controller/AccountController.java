package com.example.ecommerce.controller;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.request.AccountRequest;
import com.example.ecommerce.response.AccountResponse;
import com.example.ecommerce.response.BaseResponse;
import com.example.ecommerce.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PreAuthorize("hasAuthority('OWNER')")
    @RequestMapping(value = "/get-all-account", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getAllAccount(
            @RequestParam(name = "key_search" ,required = false, defaultValue = "") String keySearch,
            @RequestParam(name = "is_deleted",required = false, defaultValue = "-1") int isDeleted) throws Exception {
        BaseResponse response = new BaseResponse();
        List<Account> listAccounts = accountService.getAllAccount(keySearch, isDeleted);
        List<AccountResponse> responseList = new AccountResponse().mapToListResponse(listAccounts);
        response.setData(responseList);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/create-account", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> createAccount(@Valid @RequestBody AccountRequest request) throws Exception {
        BaseResponse response = new BaseResponse();
        List<Account> listAccount = null;
        listAccount = accountService.getOneAccount(request.getUsername());
        if(listAccount != null){
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessageError("Tên tài khoản đã tồn tại");
            return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
        }
        listAccount = accountService.getOneAccount(request.getPhone());
        if(listAccount != null){
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessageError("Số điện thoại đã tồn tại");
            return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);

        }
        listAccount = accountService.getOneAccount(request.getEmail());
         if(listAccount != null){
             response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessageError("Email đã tồn tại");
             return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
        }
        Account account = new Account();
        account.setFullName(request.getFullName());
        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword());
        account.setPhone(request.getPhone());
        account.setUsername(request.getUsername());
        account.setDateOfBirth(Utils.convertStringToDate(request.getDateOfBirth()));
        accountService.createAccount(account);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }
}
