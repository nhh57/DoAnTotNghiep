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
    CategoriesRepo categoryDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;
    @Autowired
    CartDetailRepo cartDetailRepo;

    CartHelper cartHelper=new CartHelper();

    @Autowired
    SessionDAO session;

    ProductHelper productHelper=new ProductHelper();
    @GetMapping("/index")
    public String index(Model model, @RequestParam Optional<String> message,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<String> txtSearch) {
        int soTrang=!soTrangString.isPresent()?1:Integer.parseInt(soTrangString.get());
        int soSanPham=!soSanPhamString.isPresent()?6:Integer.parseInt(soSanPhamString.get());
        int tongSoTrang=txtSearch.isPresent()
                ?productHelper.getTotalPage(soSanPham, productDAO.findByName(txtSearch.get()))
                :productHelper.getTotalPage(soSanPham, productDAO.findProductExist());
        if(soTrang<1){
            soTrang=1;
        }else if(soTrang>tongSoTrang){
            soTrang=tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Product> pageProduct=txtSearch.isPresent()
                ?productDAO.findByName(pageable,txtSearch.get())
                :productDAO.findAll(pageable);
        List<Product> list=pageProduct.getContent();
        if(message.isPresent()) {
            model.addAttribute("message",message.get());
        }
        model.addAttribute("listProduct",list);
        //Category
        List<Categories> listCategory=categoryDAO.findAll();
        model.addAttribute("listCategory",listCategory);
        //Set số lượng giỏ hàng
        Account khachHang=(Account) session.get("user");
        if(khachHang!=null) {
            List<CartDetail> listCart=cartDetailRepo.getCartDetail(khachHang.getCartId());
            model.addAttribute("tongSoLuongGioHang",cartHelper.getNumberOfListCart(listCart));
            model.addAttribute("sessionUsername",khachHang.getUsername());
        }else{
            model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
        }
        return "customer/index";
    }
}
