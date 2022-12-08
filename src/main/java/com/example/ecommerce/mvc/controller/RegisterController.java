package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.ShipDetail;
import com.example.ecommerce.mvc.common.MESSAGE_CONSTANT;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.helper.RegisterHelper;
import com.example.ecommerce.mvc.validate.AccountValidate;
import com.example.ecommerce.repository.AccountRepo;
import com.example.ecommerce.repository.CartRepo;
import com.example.ecommerce.repository.ShipDetailRepo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc")
public class RegisterController {
    AccountValidate accountValidate=new AccountValidate();

    @Autowired
    AccountRepo accountDAO;

    @Autowired
    CartRepo cartDAO;

    @Autowired
    SessionDAO session;

    @Autowired
    private PasswordEncoder passwordEncoder;

    RegisterHelper registerHelper=new RegisterHelper();

    public static String path="";

    @GetMapping("register")
    public String showRegister(Model model,@RequestParam Optional<String> urlReturn,
                               @ModelAttribute("user") Account user,
                               @RequestParam("registerStatus") Optional<String> registerStatus) {
        if(session.get("user")!=null){
            return "redirect:/mvc/index";
        }
        path=urlReturn.isPresent()?urlReturn.get():"";
        if(registerStatus.isPresent()){
            if(registerStatus.get().equals("username_exist")){
                model.addAttribute("registerSuccess",false);
                model.addAttribute("error",MESSAGE_CONSTANT.USERNAME_EXIST);
            }else if(registerStatus.get().equals("email_exist")){
                model.addAttribute("registerSuccess",false);
                model.addAttribute("error",MESSAGE_CONSTANT.EMAIL_EXIST);
            }else if(registerStatus.get().equals("phone_exist")){
                model.addAttribute("registerSuccess",false);
                model.addAttribute("error",MESSAGE_CONSTANT.PHONE_EXIST);
            }else if(registerStatus.get().equals("infor_empty")){
                model.addAttribute("registerSuccess",false);
                model.addAttribute("error",MESSAGE_CONSTANT.INFOR_EMPTY);
            } else if(registerStatus.get().equals("true")){
                model.addAttribute("registerSuccess",true);
            }
        }
        model.addAttribute("urlReturn",path);
        return "customer/register";
    }
    @PostMapping("register")
    public String register(@ModelAttribute("user") Account user,
                           @RequestParam("address") Optional<String> address) throws JSONException {
        List<String> listCheck=new ArrayList<>();
        listCheck.add(user.getUsername());
        listCheck.add(user.getPassword());
        listCheck.add(user.getEmail());
        listCheck.add(user.getFullName());
        listCheck.add(user.getPhone());
        if (!accountValidate.listIsNullOrEmpty(listCheck)) {
            List<Account> list = accountDAO.findAll();
            if(registerHelper.checkAccountExist(user.getUsername(),list)){
                return "redirect:/mvc/register?registerStatus=username_exist&urlReturn="+path;
            }
            if(registerHelper.checkEmailExist(user.getEmail(),list)){
                return "redirect:/mvc/register?registerStatus=email_exist&urlReturn="+path;
            }
            if(registerHelper.checkPhoneExist(user.getPhone(),list)){
                return "redirect:/mvc/register?registerStatus=phone_exist&urlReturn="+path;
            }

            Cart cartSaved=cartDAO.save(new Cart(false));
            Account account=new Account();
            account.setUsername(user.getUsername());
            account.setPassword(passwordEncoder.encode(user.getPassword()));
            account.setFullName(user.getFullName());
            account.setPhone(user.getPhone());
            account.setEmail(user.getEmail());
            account.setGender(user.getGender());
            account.setDateOfBirth(user.getDateOfBirth());
            account.setDeleted(false);
            account.setCartId(cartSaved.getId());
            accountDAO.save(account);
            return "redirect:/mvc/register?registerStatus=true&urlReturn="+path;
        }else {
            return "redirect:/mvc/register?registerStatus=infor_empty&urlReturn="+path;
        }

    }

}
