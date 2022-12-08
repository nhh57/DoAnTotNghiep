package com.example.ecommerce.mvc.controller.admin;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.OdersDetail;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.ShipDetail;
import com.example.ecommerce.model.helper.OrderHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.repository.OrderDetailRepo;
import com.example.ecommerce.repository.OrderRepo;
import com.example.ecommerce.repository.ShipDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/admin/order")
public class OrderAdminController {
    @Autowired
    OrderDetailRepo orderDetailDAO;

    @Autowired
    OrderRepo orderDAO;

    @Autowired
    ShipDetailRepo shipDetailDAO;

    @Autowired
    SessionDAO session;

    OrderHelper orderHelper=new OrderHelper();

    @GetMapping("")
    public String index(Model model,
                        @RequestParam("save") Optional<String> save,
                        @RequestParam("delete") Optional<String> delete,
                        @RequestParam("revert") Optional<String> revert,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<Integer> txtSearch){
        Account admin=(Account) session.get("admin");
        if(admin==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=order";
        }
        model.addAttribute("admin",admin);
        try{
            int soTrang = !soTrangString.isPresent() ? 1 : Integer.parseInt(soTrangString.get());
            int soSanPham = !soSanPhamString.isPresent() ? 6 : Integer.parseInt(soSanPhamString.get());
            int tongSoTrang = txtSearch.isPresent()
                    ? orderHelper.getTotalPage(soSanPham,orderDAO.findByOrderId(txtSearch.get()))
                    : orderHelper.getTotalPage(soSanPham, orderDAO.findAllCustom());
            if (soTrang < 1) {
                soTrang = 1;
            } else if (soTrang > tongSoTrang && tongSoTrang > 0) {
                soTrang = tongSoTrang;
            }
            model.addAttribute("soTrangHienTai", soTrang);
            model.addAttribute("soSanPhamHienTai", soSanPham);
            model.addAttribute("tongSoTrang", tongSoTrang);
            Pageable pageable = PageRequest.of(soTrang - 1, soSanPham);
            Page<Orders> pageOrders = txtSearch.isPresent()
                    ? orderDAO.findByOrderId(pageable,txtSearch.get())
                    : orderDAO.findAllCustom(pageable);
            List<Orders> list = pageOrders.getContent();
            model.addAttribute("listOrder",list);
            model.addAttribute("soTrangHienTai", soTrang);
            model.addAttribute("soSanPhamHienTai", soSanPham);
            model.addAttribute("tongSoTrang", tongSoTrang);
            if(txtSearch.isPresent() && txtSearch.get()!=null){
                model.addAttribute("timKiemHienTai", txtSearch.get());
            }else{
                model.addAttribute("timKiemHienTai", "");
            }
            if(save.isPresent()) {
                if(save.get().equals("true")){
                    model.addAttribute("message","Lưu lại thành công!");
                }else{
                    model.addAttribute("message","Lưu lại thất bại!");
                }
            }
            if(delete.isPresent()) {
                if(delete.get().equals("true")){
                    model.addAttribute("message","Xóa thành công!");
                }else{
                    model.addAttribute("message","Xóa thất bại!");
                }
            }
            if(revert.isPresent()) {
                if(revert.get().equals("true")){
                    model.addAttribute("message","Khôi phục thành công!");
                }else{
                    model.addAttribute("message","Khôi phục thất bại!");
                }
            }
            return "admin/order";
        }catch (Exception e){
            return "customer/404";
        }

    }

    @GetMapping("detail")
    public String detail(Model model,@RequestParam("orderId") Integer orderId){
        Account admin=(Account) session.get("admin");
        if(admin==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=order";
        }
        model.addAttribute("order", orderDAO.findById(orderId).get());
        model.addAttribute("orderDetail", orderDetailDAO.findAllByOrderId(orderId));
        return "admin/order-detail";
    }

    @PostMapping("save")
    public String save(@RequestParam("orderId") Optional<Integer> id,
                       @RequestParam("shipMethod") Optional<String> shipMethod,
                       @RequestParam("shipMethodId") Optional<Integer> shipMethodId,
                       @RequestParam("deliveryCharges") Optional<Integer> deliveryCharges,
                       @RequestParam("orderDate") Optional<String> orderDate,
                       @RequestParam("deliveryDate") Optional<String> deliveryDate,
                       @RequestParam("orderStatus") Optional<String> orderStatus,
                       @RequestParam("totalMoney") Optional<Integer> totalMoney,
                       @RequestParam("payment") Optional<String> payment,
                       @RequestParam("paymentStatus") Optional<String> paymentStatus,
                       @RequestParam("accountId") Optional<Integer> accountId,
                       @RequestParam("shipDetailId") Optional<Integer> shipDetailId,
                       @RequestParam("fullName") Optional<String> fullName,
                       @RequestParam("phone") Optional<String> phone,
                       @RequestParam("address") Optional<String> address,
                       @RequestParam("note") Optional<String> note,
                       @RequestParam("isDeleted") Optional<Boolean> isDeleted){
        Account admin=(Account) session.get("admin");
        if(admin==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=order";
        }
        try{
            Orders order = new Orders();
            order.setId(id.get());
            order.setOrderDate(Utils.converStringToDate(orderDate.get()));
            order.setDeliveryDate(Utils.converStringToDate(deliveryDate.get()));
            order.setOrderStatus(orderStatus.get());
            order.setTotalMoney(new BigDecimal(totalMoney.get()));
            order.setPaymentMethod(payment.get());
            order.setPaymentStatus(paymentStatus.get());
            order.setAccountId(accountId.get());
            order.setDeliveryCharges(deliveryCharges.get());
            order.setShipMethodId(shipMethodId.get());
            order.setShipMethod(shipMethod.get());
            if(isDeleted.isPresent()){
                order.setIsDeleted(isDeleted.get());
            }else{
                order.setIsDeleted(false);
            }
            ShipDetail shipDetail=shipDetailDAO.findByFullNameAndPhoneAndAddress(fullName.get(),phone.get(),address.get());
            if(shipDetail!=null){
                order.setShipDetailId(shipDetailId.get());
            }else{
                ShipDetail shipDetailSaved=shipDetailDAO.save(new ShipDetail(phone.get(),address.get(),fullName.get(),accountId.get(),false,false));
                order.setShipDetailId(shipDetailSaved.getId());
            }
            order.setNote(note.get());
            orderDAO.save(order);
            return "redirect:/mvc/admin/order?save=true";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/mvc/admin/order?save=false";
        }

    }
    @GetMapping("delete")
    public String delete(@RequestParam("orderId") Optional<String> orderId){
        Account admin=(Account) session.get("admin");
        if(admin==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=order";
        }
        try{
            Integer id=Integer.parseInt(orderId.get());
            List<OdersDetail> list=orderDetailDAO.findAllByOrderIdAdmin(id);
            for(OdersDetail o:list){
                o.setDeleted(true);
                orderDetailDAO.save(o);
            }
            Orders oders=orderDAO.findById(id).get();
            oders.setIsDeleted(true);
            orderDAO.save(oders);
            return "redirect:/mvc/admin/order?delete=true";
        }catch (Exception e){
            return "redirect:/mvc/admin/order?delete=false";
        }
    }
    @GetMapping("revert")
    public String revert(@RequestParam("orderId") Optional<String> orderId){
        Account admin=(Account) session.get("admin");
        if(admin==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=order";
        }
        try{
            Integer id=Integer.parseInt(orderId.get());
            List<OdersDetail> list=orderDetailDAO.findAllByOrderIdAdmin(id);
            for(OdersDetail o:list){
                o.setDeleted(false);
                orderDetailDAO.save(o);
            }
            Orders oders=orderDAO.findById(id).get();
            oders.setIsDeleted(false);
            orderDAO.save(oders);
            return "redirect:/mvc/admin/order?revert=true";
        }catch (Exception e){
            return "redirect:/mvc/admin/order?revert=false";
        }
    }
}
