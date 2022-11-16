package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.repository.AccountRepo;
import com.example.ecommerce.repository.CartDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mvc/information")
public class InforController {
    @Autowired
    ShoppingCartDAO shoppingCartDAO;
    @Autowired
    CartDetailRepo cartDetailRepo;

    @Autowired
    AccountRepo accountRepo;

    CartHelper cartHelper = new CartHelper();

    @Autowired
    SessionDAO session;

    @GetMapping("")
    public String view(Model model) {
        Account khachHang = (Account) session.get("user");
        if (khachHang == null) {
            return "redirect:/mvc/login?urlReturn=information";
        }
        Integer cartId = khachHang != null ? khachHang.getCartId() : null;
        if (khachHang != null) {
            shoppingCartDAO.getAll().forEach(item -> {
                CartDetail cartDetail = cartDetailRepo.existByProductId(cartId, item.getId());
                if (cartDetail != null) {
                    cartDetail.setAmount(cartDetail.getAmount() + item.getSoLuong());
                    cartDetailRepo.saveAndFlush(cartDetail);
                } else {
                    CartDetail cartDetailNew = new CartDetail();
                    cartDetailNew.setCartId(cartId);
                    cartDetailNew.setProductId(item.getId());
                    cartDetailNew.setAmount(item.getSoLuong());
                    cartDetailRepo.saveAndFlush(cartDetailNew);
                }
            });
            shoppingCartDAO.clear();

            List<CartDetail> listCart = cartDetailRepo.getCartDetail(khachHang.getCartId());
            model.addAttribute("tongSoLuongGioHang", cartHelper.getNumberOfListCart(listCart));
            model.addAttribute("sessionUsername", khachHang.getUsername());
            model.addAttribute("userInfor",accountRepo.findByUsername(khachHang.getUsername()));
        }
        return "customer/infor";
    }

    @PostMapping("save")
    public String save(@RequestParam Optional<String> id,
                       @RequestParam Optional<String> username,
                       @RequestParam Optional<String> fullName,
                       @RequestParam Optional<String> email,
                       @RequestParam Optional<String> phone,
                       @RequestParam Optional<String> gender,
                       @RequestParam Optional<String> dateOfBirth){
        Date date=new Date();
        date.getTime();
        int a=1;
        return null;
    }
}
