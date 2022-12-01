package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.*;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String view(Model model, @ModelAttribute("shipDetail") ShipDetail shipDetail,
                       @RequestParam("paymentNull") Optional<String> paymentNull,
                       @RequestParam("addressNull") Optional<String> addressNull,
                       @RequestParam("success") Optional<String> success,
                       @RequestParam("orderId") Optional<String> orderId) {
        Account khachHang = session.get("user") != null ? (Account) session.get("user") : null;
        Integer cartId = khachHang != null ? khachHang.getCartId() : null;
        if (khachHang == null) {
            return "redirect:/mvc/login?error=errorNoLogin&urlReturn=order";
        }
        if (addressNull.isPresent()) {
            model.addAttribute("addressNull", "Vui lòng thêm 1 địa chỉ!");
        }
        if (paymentNull.isPresent()) {
            model.addAttribute("paymentNull", "Chọn 1 phương thức thanh toán!");
        }
        if(success.isPresent()){
            if(success.get().equals("shipDetail")){
                model.addAttribute("success","Thêm địa chỉ thành công!");
            }
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

    @PostMapping("ship-detail/add")
    public String shipDetailAdd(@ModelAttribute("shipDetail") ShipDetail shipDetail) {
        Account account = (Account) session.get("user");
        if (account == null) {
            return "redirect:/mvc/login?urlReturn=information";
        }
        if(shipDetail!=null){
            List<ShipDetail> list=shipDetailRepo.findByAccountId(account.getId());
            if(list.isEmpty() || list ==null){
                shipDetail.setDefault(true);
            }else{
                shipDetail.setDefault(false);
            }
            shipDetail.setDeleted(false);
            shipDetail.setAccountId(account.getId());
            shipDetailRepo.save(shipDetail);
        }
        return "redirect:/mvc/order?success=shipDetail";
    }
}
