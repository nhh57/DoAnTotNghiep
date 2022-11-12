package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("mvc/single-product")
public class ProductDetailController {
    @Autowired
    ProductRepo productDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;

    @Autowired
    SessionDAO session;

    @GetMapping("")
    public String index(Model model, @RequestParam("id") Optional<String> productIdParam){
        if(productIdParam.isPresent()){
            int productId=Integer.parseInt(productIdParam.get());
            Product product=productDAO.findById(productId).get();
            model.addAttribute("product",product);
            model.addAttribute("category",product.getCategoriesByCategoryId());
            model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
            Account khachHang=(Account) session.get("user");
            if(khachHang!=null) {
                model.addAttribute("sessionUsername",khachHang.getUsername());
            }
        }
        return "customer/single-product";
    }
}
