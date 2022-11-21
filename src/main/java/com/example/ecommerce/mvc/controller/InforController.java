package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.ShipDetail;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.mvc.model.OrderResult;
import com.example.ecommerce.mvc.model.OrderStatus;
import com.example.ecommerce.repository.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
    ShipDetailRepo shipDetailRepo;
    @Autowired
    OrderDetailRepo orderDetailRepo;

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    AccountRepo accountRepo;

    CartHelper cartHelper = new CartHelper();

    @Autowired
    SessionDAO session;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private List<OrderResult> getListOrderResult(List<Orders> list){
        List<OrderResult> listOrderResult=new ArrayList<>();
        for (Orders item : list) {
            OrderResult orderResult = new OrderResult();
            orderResult.setOrders(item);
            orderResult.setListOrderDetail(orderDetailRepo.findAllByOrderId(item.getId()));
            listOrderResult.add(orderResult);
        }
        return listOrderResult;
    }
    @GetMapping("")
    public String view(Model model,@ModelAttribute("shipDetail") ShipDetail shipDetail,
                       @RequestParam Optional<String> tag,
                       @RequestParam Optional<String> success,
                       @RequestParam Optional<String> errorPass,
                       @RequestParam Optional<String> currentPassword,
                       @RequestParam Optional<String> newPassword,
                       @RequestParam Optional<String> confirmPassword,
                       @RequestParam Optional<Integer> orderId,
                       @RequestParam Optional<String> productName) {
        Account khachHang = (Account) session.get("user");
        if (khachHang == null) {
            return "redirect:/mvc/login?error=errorNoLogin&urlReturn=information";
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
            model.addAttribute("listShipDetail",shipDetailRepo.findByAccountId(khachHang.getId()));
        }
        List<OrderStatus> listOrderStatus= Arrays.asList(
                new OrderStatus("listTatCa",""),
                new OrderStatus("listChoXacNhan","Chờ xác nhận"),
                new OrderStatus("listChoLayHang","Chờ lấy hàng"),
                new OrderStatus("listDangGiao","Đang giao"),
                new OrderStatus("listDaGiao","Đã giao"),
                new OrderStatus("listDaHuy","Đã hủy"),
                new OrderStatus("listHoanTien","Hoàn tiền")
        );
        if(orderId.isPresent()){
            for(OrderStatus i:listOrderStatus){
                List<Orders> list=orderRepo.findAllByAccountIdAndOrderIdAndOrderStatus(khachHang.getId(),orderId.get(), i.getValue());
                model.addAttribute(i.getKey(), getListOrderResult(list));
            }
        }else{
            for(OrderStatus i:listOrderStatus){
                List<Orders> list=orderRepo.findAllByAccountIdAndOrderStatus(khachHang.getId(), i.getValue());
                model.addAttribute(i.getKey(), getListOrderResult(list));
            }
        }
        if(tag.isPresent()){
            if(tag.get().equals("change-password")){
                model.addAttribute("clickElement","doiMatKhau");
            }else if(tag.get().equals("information")){
                model.addAttribute("clickElement","hoSo");
            }else if(tag.get().equals("order")){
                model.addAttribute("clickElement","donMua");
            }
        }

        if(success.isPresent()){
            String keyClickElement="clickElement";
            String keySuccess="success";
            if(success.get().equals("saved")){
                model.addAttribute(keyClickElement,"hoSo");
                model.addAttribute(keySuccess,"Lưu thành công!");
            }else if(success.get().equals("changePass")){
                model.addAttribute(keyClickElement,"doiMatKhau");
                model.addAttribute(keySuccess,"Đổi mật khẩu thành công!");
                model.addAttribute("changePassSuccess",true);
            }else if(success.get().equals("shipDetail")){
                model.addAttribute("clickElement","diaChi");
                model.addAttribute(keySuccess,"Thêm địa chỉ thành công!");
            }else if(success.get().equals("shipDetail2")){
                model.addAttribute(keyClickElement,"diaChi");
                model.addAttribute(keySuccess,"Cập nhật địa chỉ thành công!");
            }else if(success.get().equals("shipDetail3")){
                model.addAttribute(keyClickElement,"diaChi");
                model.addAttribute(keySuccess,"Xóa địa chỉ thành công!");
            }
        }
        if(errorPass.isPresent()){
            if(errorPass.get().equals("0")){
                model.addAttribute("clickElement","doiMatKhau");
                model.addAttribute("currentPassValue",currentPassword.get());
                model.addAttribute("newPassValue",newPassword.get());
                model.addAttribute("confirmPassValue",confirmPassword.get());
                model.addAttribute("errorPass",true);
            }else if(errorPass.get().equals("1")){
                model.addAttribute("clickElement","doiMatKhau");
                model.addAttribute("currentPassValue",currentPassword.get());
                model.addAttribute("newPassValue",newPassword.get());
                model.addAttribute("confirmPassValue",confirmPassword.get());
                model.addAttribute("errorRePass",true);
            }

        }
        return "customer/infor";
    }

    @PostMapping("save")
    public String save(@RequestParam Optional<String> username,
                       @RequestParam Optional<String> fullName,
                       @RequestParam Optional<String> email,
                       @RequestParam Optional<String> phone,
                       @RequestParam Optional<String> gender,
                       @RequestParam Optional<String> dateOfBirth) throws ParseException {
        Account khachHang = (Account) session.get("user");
        if (khachHang == null) {
            return "redirect:/mvc/login?urlReturn=information";
        }
        Account account=accountRepo.findByUsername(username.get());
        account.setFullName(fullName.get());
        account.setEmail(email.get());
        account.setPhone(phone.get());
        account.setGender(gender.get());
        account.setDateOfBirth(Utils.converStringToDate(dateOfBirth.get()));
        accountRepo.save(account);
        return "redirect:/mvc/information?success=saved";
    }
    @PostMapping("ship-detail/add")
    public String shipDetailAdd(@ModelAttribute("shipDetail") ShipDetail shipDetail) {
        Account account = (Account) session.get("user");
        if (account == null) {
            return "redirect:/mvc/login?urlReturn=information";
        }
        if(shipDetail!=null){
            shipDetail.setDeleted(false);
            shipDetail.setAccountId(account.getId());
            shipDetailRepo.save(shipDetail);
        }
        return "redirect:/mvc/information?success=shipDetail";
    }
    @PostMapping("ship-detail/update")
    public String shipDetailUpdate( @RequestParam Optional<Integer> id,
                                    @RequestParam Optional<String> fullName,
                                    @RequestParam Optional<String> phone,
                                    @RequestParam Optional<String> address) {
        Account account = (Account) session.get("user");
        if (account == null) {
            return "redirect:/mvc/login?urlReturn=information";
        }
        ShipDetail shipDetail=new ShipDetail();
        shipDetail.setId(id.get());
        shipDetail.setFullName(fullName.get());
        shipDetail.setPhone(phone.get());
        shipDetail.setAddress(address.get());
        shipDetail.setDeleted(false);
        shipDetail.setAccountId(account.getId());
        shipDetailRepo.save(shipDetail);
        return "redirect:/mvc/information?success=shipDetail2";
    }

    @PostMapping("ship-detail/delete")
    public String shipDetailUpdate( @RequestParam Optional<Integer> id) {
        Account account = (Account) session.get("user");
        if (account == null) {
            return "redirect:/mvc/login?urlReturn=information";
        }
        ShipDetail shipDetail=shipDetailRepo.findById(id.get()).get();
        shipDetail.setDeleted(true);
        shipDetailRepo.save(shipDetail);
        return "redirect:/mvc/information?success=shipDetail3";
    }
    @PostMapping("change-password")
    public String changePassword(@RequestParam Optional<String> currentPassword,
                                 @RequestParam Optional<String> newPassword,
                                 @RequestParam Optional<String> confirmPassword){
        Account account = (Account) session.get("user");
        if (account == null) {
            return "redirect:/mvc/login?urlReturn=information";
        }
        if(!passwordEncoder.matches(currentPassword.get(),account.getPassword())){
            //Mật khẩu cũ không đúng
            return "redirect:/mvc/information?errorPass=0&currentPassword="
                    +currentPassword.get()+"&newPassword="+newPassword.get()+"&confirmPassword="+confirmPassword.get();
        }
        if(!newPassword.get().equals(confirmPassword.get())){
            //Nhập lại mật khẩu không đúng
            return "redirect:/mvc/information?errorPass=1&currentPassword="
                    +currentPassword.get()+"&newPassword="+newPassword.get()+"&confirmPassword="+confirmPassword.get();
        }
        account.setPassword(passwordEncoder.encode(newPassword.get()));
        accountRepo.save(account);
        return "redirect:/mvc/information?success=changePass";
    }

    @PostMapping("/ship-detail/setDefault")
    public ResponseEntity<String> setDefault(@RequestParam("shipDetailId") Integer shipDetailId,
                                             @RequestParam("accountId") Integer accountId) throws JSONException {
        List<ShipDetail> list=shipDetailRepo.findByAccountId(accountId);
        for(ShipDetail item:list){
            item.setDefault(false);
            shipDetailRepo.save(item);
        }
        ShipDetail shipDetail=shipDetailRepo.findById(shipDetailId).get();
        shipDetail.setDefault(true);
        shipDetailRepo.save(shipDetail);
        JSONObject json = new JSONObject();
        json.put("status", "Thành công");
        return ResponseEntity.ok(String.valueOf(json));
    }
    @PostMapping("/order/setOrderStatus")
    public ResponseEntity<String> setOrderStatus(@RequestParam("orderStatus") String orderStatus,
                                             @RequestParam("orderId") Integer orderId) throws JSONException {
        Orders orders=orderRepo.findById(orderId).get();
        orders.setOrderStatus(orderStatus);
        orderRepo.save(orders);
        JSONObject json = new JSONObject();
        json.put("status", "Thành công");
        return ResponseEntity.ok(String.valueOf(json));
    }
}
