package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.mvc.helper.ProductHelper;
import com.example.ecommerce.repository.CartDetailRepo;
import com.example.ecommerce.repository.CategoriesRepo;
import com.example.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc")
public class IndexMVCController {
    @Autowired
    ProductRepo productDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;
    @Autowired
    CartDetailRepo cartDetailRepo;

    CartHelper cartHelper = new CartHelper();

    @Autowired
    SessionDAO session;

    ProductHelper productHelper = new ProductHelper();

    @GetMapping("/index")
    public String index(Model model, @RequestParam Optional<String> message) {
        if (message.isPresent()) {
            model.addAttribute("message", message.get());
        }
        Pageable pageable = PageRequest.of(0, 4);
        Page<Product> pageProduct = productDAO.findByBrandId(pageable,1);
        List<Product> list1 = pageProduct.getContent();
        List<Product> list2 = productDAO.findByBrandId(pageable,2).getContent();
        List<Product> list3 = productDAO.findByBrandId(pageable,3).getContent();
        model.addAttribute("listProductByBrandId1", list1);
        model.addAttribute("listProductByBrandId2", list2);
        model.addAttribute("listProductByBrandId3", list2);
        model.addAttribute("listBestSelling", productDAO.findByBestSellingProducts(10));
        //Set số lượng giỏ hàng
        Account khachHang = (Account) session.get("user");
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
        } else {
            model.addAttribute("tongSoLuongGioHang", shoppingCartDAO.getCount());
        }
        return "customer/index";
    }
}
