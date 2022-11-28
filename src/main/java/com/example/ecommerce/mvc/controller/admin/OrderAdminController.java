package com.example.ecommerce.mvc.controller.admin;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.OdersDetail;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.helper.OrderHelper;
import com.example.ecommerce.repository.OrderDetailRepo;
import com.example.ecommerce.repository.OrderRepo;
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

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/admin/order")
public class OrderAdminController {
    @Autowired
    OrderDetailRepo orderDetailDAO;

    @Autowired
    OrderRepo orderDAO;

//    @Autowired
//    HttpServletRequest request;

    OrderHelper orderHelper=new OrderHelper();

    @GetMapping("")
    public String index(Model model,
                        @RequestParam("save") Optional<String> save,
                        @RequestParam("delete") Optional<String> delete,
                        @RequestParam("revert") Optional<String> revert,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString){
//        if(!(request.isUserInRole("1") || request.isUserInRole("2"))) {
//            return "redirect:/auth/access/denied";
//        }

        Timestamp a=orderDAO.findAll().get(0).getOrderDate();
        int soTrang = !soTrangString.isPresent() ? 1 : Integer.parseInt(soTrangString.get());
        int soSanPham = !soSanPhamString.isPresent() ? 6 : Integer.parseInt(soSanPhamString.get());
        int tongSoTrang = orderHelper.getTotalPage(soSanPham, orderDAO.findAll());
        if (soTrang < 1) {
            soTrang = 1;
        } else if (soTrang > tongSoTrang) {
            soTrang = tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        Pageable pageable = PageRequest.of(soTrang - 1, soSanPham);
        Page<Orders> pageOrders = orderDAO.findAll(pageable);
        List<Orders> list = pageOrders.getContent();
        model.addAttribute("listOrder",list);
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
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
    }

    @GetMapping("detail")
    public String detail(Model model,@RequestParam("orderId") Integer orderId){
        model.addAttribute("order", orderDAO.findById(orderId).get());
        model.addAttribute("orderDetail", orderDetailDAO.findAllByOrderId(orderId));
        return "admin/order-detail";
    }

//    @PostMapping("save")
//    public String save(@RequestParam("orderId") Optional<Integer> id,
//                       @RequestParam("orderDate") Optional<String> orderDate,
//                       @RequestParam("deliveryDate") Optional<String> deliveryDate,
//                       @RequestParam("orderStatus") Optional<String> orderStatus,
//                       @RequestParam("totalMoney") Optional<Integer> totalMoney,
//                       @RequestParam("payment") Optional<String> payment,
//                       @RequestParam("paymentStatus") Optional<String> paymentStatus,
//                       @RequestParam("accountId") Optional<Integer> accountId,
//                       @RequestParam("shipDetailId") Optional<Integer> shipDetailId,
//                       @RequestParam("note") Optional<String> note,
//                       @RequestParam("isDeleted") Optional<Boolean> isDeleted,
//                       HttpServletRequest req){
////        if(!(request.isUserInRole("1") || request.isUserInRole("2"))) {
////            return "redirect:/auth/access/denied";
////        }
//        try{
//            Orders order = new Orders();
//            order.setId(id.get());
//            order.setOrderDate(Utils.convertStringTo(orderDate.get()));
//            order.setDeliveryDate(Utils.converStringToDate(deliveryDate.get()));
//            order.setOrderStatus(orderStatus.get());
//            order.setTotalMoney(totalMoney.get());
//            order.setPaymentMethod(payment.get());
//            order.setPaymentStatus(paymentStatus.get());
//            order.setAccountId(accountId.get());
//            if(isDeleted.isPresent()){
//                order.setIsDeleted(isDeleted.get());
//            }else{
//                order.setIsDeleted(false);
//            }
//            order.setShipDetailId(shipDetailId.get());
//            order.setNote(note.get());
//            return "redirect:/mvc/admin/order?save=true";
//        }catch (Exception e){
//            return "redirect:/mvc/admin/order?save=false";
//        }
//
//    }

    @GetMapping("delete")
    public String delete(@RequestParam("orderId") Optional<String> orderId){
        try{
            Integer id=Integer.parseInt(orderId.get());
            List<OdersDetail> list=orderDetailDAO.findAllByOrderId(id);
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
        try{
            Integer id=Integer.parseInt(orderId.get());
            List<OdersDetail> list=orderDetailDAO.findAllByOrderId(id);
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
