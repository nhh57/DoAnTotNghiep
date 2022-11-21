package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.*;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.model.helper.OrderHelper;
import com.example.ecommerce.mvc.common.PAYPAL_URL;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.mvc.model.ShoppingCart;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/order")
public class OrderMVCController {
    @Autowired
    SessionDAO session;
    @Autowired
    ShipDetailRepo shipDetailRepo;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;
    @Autowired
    CartDetailRepo cartDetailRepo;

    @Autowired
    ProductRepo productDAO;

    CartHelper cartHelper = new CartHelper();


    @GetMapping("")
    public String view(Model model,
                       @RequestParam("addressNull") Optional<String> addressNull,
                       @RequestParam("orderId") Optional<String> orderId) {
        Account khachHang = session.get("user") != null ? (Account) session.get("user") : null;
        Integer cartId = khachHang != null ? khachHang.getCartId() : null;
        if (khachHang == null) {
            return "redirect:/mvc/login?error=errorNoLogin&urlReturn=order";
        }
        if (addressNull.isPresent()) {
            model.addAttribute("addressNull", "Chọn hoặc điền 1 địa chỉ!");
        }
        if (khachHang != null) {
            model.addAttribute("sessionUsername", khachHang.getUsername());
            model.addAttribute("user", khachHang);
        }
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
        if (khachHang != null && cartId != null) {
            List<CartDetail> listCart = cartDetailRepo.getCartDetail(cartId);
            if (listCart.size() > 0) {
                if (cartHelper.checkNullProductByProductId(listCart)) {
                    for (CartDetail item : listCart) {
                        item.setProductByProductId(productDAO.findById(item.getProductId()).get());
                    }
                }
            }
            model.addAttribute("cartId", cartId);
            model.addAttribute("checkCart", true);
            model.addAttribute("listGioHangLogin", listCart);
            model.addAttribute("listDiaChi", shipDetailRepo.findByAccountId(khachHang.getId()));
            model.addAttribute("diaChiMacDinh", shipDetailRepo.findByAccountIdAndIsDefault(khachHang.getId()));
            model.addAttribute("tongTienGioHang", cartHelper.getTotalMoneyCart(listCart));
            model.addAttribute("tongSoLuongGioHang", cartHelper.getNumberOfListCart(listCart));
        }
        return "customer/order";
    }
}
