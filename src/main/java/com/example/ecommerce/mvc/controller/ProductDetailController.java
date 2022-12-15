package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductImage;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.repository.CartDetailRepo;
import com.example.ecommerce.repository.ProductImageRepo;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.repository.ReviewRepo;
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
@RequestMapping("mvc/single-product")
public class ProductDetailController {
    @Autowired
    ProductRepo productDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;
    @Autowired
    CartDetailRepo cartDetailRepo;

    @Autowired
    ReviewRepo reviewRepo;

    @Autowired
    ProductImageRepo productImageRepo;

    CartHelper cartHelper=new CartHelper();

    @Autowired
    SessionDAO session;

    @GetMapping("")
    public String index(Model model, @RequestParam("id") Optional<String> productIdParam){
        try{
            model.addAttribute("productIdParam",productIdParam.get());
            int productId=Integer.parseInt(productIdParam.get());
            Product product=productDAO.findById(productId).get();
            model.addAttribute("product",product);
            Pageable pageable = PageRequest.of(0, 4);
            Page<Product> pageProduct = productDAO.findByBrandIdAndCategoryId(pageable,product.getCategoryId(),product.getBrandId(),productId);
            List<Product> list = pageProduct.getContent();
            model.addAttribute("listProductLienQuan",list);
            //Set số lượng giỏ hàng
            Account khachHang=(Account) session.get("user");
            if(khachHang!=null) {
                List<CartDetail> listCart=cartDetailRepo.getCartDetail(khachHang.getCartId());
                model.addAttribute("tongSoLuongGioHang",cartHelper.getNumberOfListCart(listCart));
                model.addAttribute("sessionUsername",khachHang.getUsername());
            }else{
                model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
            }
            List<ProductImage> listProductImage=productImageRepo.findByProductId(productId);
            model.addAttribute("listProductImage",listProductImage);
            model.addAttribute("sizeListMoreImage",listProductImage.size());
            model.addAttribute("listReviews",reviewRepo.findByProductId(productId));
            return "customer/single-product";
        }catch (Exception e){
            return "customer/404";
        }
    }
}
