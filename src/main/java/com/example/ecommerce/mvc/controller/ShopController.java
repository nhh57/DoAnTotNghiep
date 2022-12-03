package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.*;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.mvc.helper.ProductHelper;
import com.example.ecommerce.mvc.model.OrderResult;
import com.example.ecommerce.mvc.model.ProductMVCResult;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/shop")
public class ShopController {
    @Autowired
    ProductRepo productDAO;

    @Autowired
    CategoriesRepo categoryDAO;

    @Autowired
    BrandRepo brandDAO;
    @Autowired
    ShoppingCartDAO shoppingCartDAO;
    @Autowired
    CartDetailRepo cartDetailRepo;

    CartHelper cartHelper = new CartHelper();

    @Autowired
    SessionDAO session;

    ProductHelper productHelper = new ProductHelper();



    @GetMapping("")
    public String index(Model model, @RequestParam Optional<String> message,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<String> txtSearch) {
        if(txtSearch.isPresent() && txtSearch.get()!=null){
            model.addAttribute("timKiemHienTai", txtSearch.get());
        }else{
            model.addAttribute("timKiemHienTai", "");
        }
        int soTrang = !soTrangString.isPresent() ? 1 : Integer.parseInt(soTrangString.get());
        int soSanPham = !soSanPhamString.isPresent() ? 8 : Integer.parseInt(soSanPhamString.get());
        int tongSoTrang = txtSearch.isPresent()
                ? productHelper.getTotalPage(soSanPham, productDAO.findByName(txtSearch.get()))
                : productHelper.getTotalPage(soSanPham, productDAO.findProductExist());
        if (soTrang < 1) {
            soTrang = 1;
        } else if (soTrang > tongSoTrang) {
            soTrang = tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        Pageable pageable = PageRequest.of(soTrang - 1, soSanPham);
        Page<Product> pageProduct = txtSearch.isPresent()
                ? productDAO.findByName(pageable, txtSearch.get())
                : productDAO.findAll(pageable);
        List<Product> list = pageProduct.getContent();
        if (message.isPresent()) {
            model.addAttribute("message", message.get());
        }
        model.addAttribute("listProduct", productHelper.getListProductMVCResult(list));
        //Category
        model.addAttribute("listCategory", categoryDAO.findAll());
        //Brand
        model.addAttribute("listBrand", brandDAO.findAll());
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
        return "customer/shop";
    }

}
