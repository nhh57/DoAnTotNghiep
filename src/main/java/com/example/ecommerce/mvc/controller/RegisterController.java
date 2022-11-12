package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.message.MESSAGE_CONSTANT;
import com.example.ecommerce.mvc.validate.AccountValidate;
import com.example.ecommerce.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@Controller
@RequestMapping("mvc")
public class RegisterController {
    AccountValidate accountValidate=new AccountValidate();

    @Autowired
    AccountRepo accountDAO;


    @Autowired
    SessionDAO session;

    @GetMapping("register")
    public String showRegister(Model model,@ModelAttribute("user") Account user) {
        return "customer/register";
    }
    @PostMapping("register")
    public String register(Model model,
                        @ModelAttribute("user") Account user) {
        if(session.get("user")!=null){
            return "redirect:/mvc/index";
        }
        boolean registerSuccess = true;
        List<String> listCheck=new ArrayList<>();
        listCheck.add(user.getUsername());
        listCheck.add(user.getPassword());
        listCheck.add(user.getEmail());
        listCheck.add(user.getFullName());
        listCheck.add(user.getPhone());
        if (!accountValidate.listIsNullOrEmpty(listCheck)) {
            List<Account> list = accountDAO.findAll();
            for (Account account : list) {
                if (user.getUsername().equalsIgnoreCase(account.getUsername())) {
                    model.addAttribute("message", MESSAGE_CONSTANT.USERNAME_EXIST);
                    registerSuccess = false;
                    return "customer/register";
                }
            }
        }
        if(registerSuccess) {
            Account account=new Account();
            account.setUsername(user.getUsername());
            account.setPassword(user.getPassword());
            account.setFullName(user.getFullName());
            account.setPhone(user.getPhone());
            account.setEmail(user.getEmail());
            account.setGender(user.getGender());
            account.setDateOfBirth(user.getDateOfBirth());
//            account.set(roleDAO.findById(3).get());
//            accountDAO.save(account);
            return "redirect:/mvc/login";
        }else {
            return "redirect:/mvc/register";
        }

    }

}
