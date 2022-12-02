package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.mvc.dao.MailerService;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.mvc.helper.MailerHelper;
import com.example.ecommerce.mvc.model.MailInfo;
import com.example.ecommerce.mvc.model.ShoppingCart;
import com.example.ecommerce.repository.CartDetailRepo;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/contact")
public class ContactController {
    @Autowired
    MailerService mailer;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;

    @Autowired
    CartDetailRepo cartDetailRepo;

    @Autowired
    SessionDAO session;

    CartHelper cartHelper=new CartHelper();

    @GetMapping("")
    public String index(Model model,
                        @ModelAttribute("user") Account user) {
        Account khachHang=session.get("user") != null ? (Account) session.get("user") : null;
        Integer cartId= khachHang != null ? khachHang.getCartId() : null;
        if(khachHang!=null) {
            model.addAttribute("sessionUsername",khachHang.getUsername());
            model.addAttribute("user",khachHang);
            shoppingCartDAO.getAll().forEach(item -> {
                CartDetail cartDetail=cartDetailRepo.existByProductId(cartId,item.getId());
                if(cartDetail != null){
                    cartDetail.setAmount(cartDetail.getAmount()+item.getSoLuong());
                    cartDetailRepo.saveAndFlush(cartDetail);
                }else{
                    CartDetail cartDetailNew=new CartDetail();
                    cartDetailNew.setCartId(cartId);
                    cartDetailNew.setProductId(item.getId());
                    cartDetailNew.setAmount(item.getSoLuong());
                    cartDetailRepo.saveAndFlush(cartDetailNew);
                }
            });
            shoppingCartDAO.clear();
        }
        if(khachHang != null && cartId != null){
            List<CartDetail> listCart=cartDetailRepo.getCartDetail(cartId);
            model.addAttribute("tongSoLuongGioHang",cartHelper.getNumberOfListCart(listCart));
        }else{
            model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
        }
        return "customer/contact";
    }

    @PostMapping("")
    public ResponseEntity<String> sendMail(@RequestParam("fullName") Optional<String> fullName,
                                           @RequestParam("email") Optional<String> email,
                                           @RequestParam("phone") Optional<String> phone,
                                           @RequestParam("subject") Optional<String> subject,
                                           @RequestParam("message") Optional<String> message) throws JSONException {
        MailerHelper helper = new MailerHelper();
        MailInfo mail = new MailInfo();
        mail.setTo("websitebandongho@gmail.com");
        mail.setSubject(subject.isPresent() ? subject.get() : "<Không có tiêu đề>");
        mail.setBody(helper.getContact(message.get(), fullName.get(), email.get(), phone.get()));
        //Gửi mail
        mailer.queue(mail);
        JSONObject json = new JSONObject();
        json.put("status", "Email của bạn đã được gửi đi");
        return ResponseEntity.ok(String.valueOf(json));
    }
}
