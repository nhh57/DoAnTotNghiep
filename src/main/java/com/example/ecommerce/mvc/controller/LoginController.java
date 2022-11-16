package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.validate.AccountValidate;
import com.example.ecommerce.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc")
public class LoginController {
    String path="";
    AccountValidate accountValidate=new AccountValidate();
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepo accountDAO;
    @Autowired
    SessionDAO session;

    @GetMapping("/login")
    public String login(Model model, @RequestParam Optional<String> urlReturn,
                        @RequestParam Optional<String> error) {
        if(session.get("user")!=null){
            return "redirect:/mvc/index";
        }
        path=urlReturn.isPresent()?urlReturn.get():"";
        if(error.isPresent()) {
            if(error.get().equals("errorNoLogin")){
                model.addAttribute("message", "Vui lòng đăng nhập để tiếp tục!");
            }else if(error.get().equals("LoginFail")){
                model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu");
            }
        }
        return "customer/login";
    }

    @GetMapping("/logout")
    public String logout(@RequestParam Optional<String> urlReturn) {
        session.clear();
        return urlReturn.isPresent()?"redirect:/mvc/" +urlReturn.get():"redirect:/mvc/index";
    }

    @PostMapping("/login")
    public String login(Model model, @RequestParam String username, @RequestParam String password) {
        boolean loginSuccess = false;
        List<String> listCheck=new ArrayList<>();
        listCheck.add(username);
        listCheck.add(password);
        if (!accountValidate.listIsNullOrEmpty(listCheck)) {
            List<Account> list = accountDAO.findAll();
            for (Account account : list) {
                if (username.equalsIgnoreCase(account.getUsername()) && passwordEncoder.matches(password,account.getPassword())) {
                    session.set("user", account);
                    loginSuccess = true;
                    break;
                }
            }
        }
        if(loginSuccess) {
            return !path.isEmpty() ? "redirect:/mvc/"+path : "redirect:/mvc/index";
        }else {
            return !path.isEmpty() ? "redirect:/mvc/login?error=LoginFail&urlReturn="+path :"redirect:/mvc/login?error=LoginFail";
        }
    }

}