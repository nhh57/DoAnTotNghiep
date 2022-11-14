package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.repository.CartDetailRepo;
import com.example.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/single-product")
public class ProductDetailController {
    @Autowired
    ProductRepo productDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;
    @Autowired
    CartDetailRepo cartDetailRepo;

    CartHelper cartHelper=new CartHelper();

    @Autowired
    SessionDAO session;

    @GetMapping("")
    public String index(Model model, @RequestParam("id") Optional<String> productIdParam){
        if(productIdParam.isPresent()){
            int productId=Integer.parseInt(productIdParam.get());
            Product product=productDAO.findById(productId).get();
            model.addAttribute("product",product);
            model.addAttribute("category",product.getCategoriesByCategoryId());
            //Set số lượng giỏ hàng
            Account khachHang=(Account) session.get("user");
            if(khachHang!=null) {
                List<CartDetail> listCart=cartDetailRepo.getCartDetail(khachHang.getCartId());
                model.addAttribute("tongSoLuongGioHang",cartHelper.getNumberOfListCart(listCart));
                model.addAttribute("sessionUsername",khachHang.getUsername());
            }else{
                model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
            }
        }
        return "customer/single-product";
    }
}
