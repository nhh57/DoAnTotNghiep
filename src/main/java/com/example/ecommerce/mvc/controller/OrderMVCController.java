package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.*;
import com.example.ecommerce.model.helper.OrderHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/order")
public class OrderMVCController {
    @Autowired
    OrderRepo orderDAO;
    @Autowired
    OrderDetailRepo orderDetailDAO;

    @Autowired
    ProductRepo productRepo;
    @Autowired
    SessionDAO session;
    @Autowired
    ShipDetailRepo shipDetailRepo;

    @Autowired
    CartDetailRepo cartDetailRepo;

    OrderHelper orderHelper = new OrderHelper();

    @PostMapping("/add")
    public String add(@ModelAttribute("user") Account user,
                      @RequestParam("total-money") Optional<Integer> totalMoney,
                      @RequestParam("cart-id") Optional<Integer> cartId,
                      @RequestParam("address") Optional<String> address,
                      @RequestParam("ship-detail-id") Optional<String> shipDetailId,
                      @RequestParam("payment-method") Optional<String> paymentMethod,
                      @RequestParam("note") Optional<String> note) {
        if ((address.get().isEmpty() || address.get() == null) && (!shipDetailId.isPresent())) {
            return "redirect:/mvc/shopping-cart?addressNull=true";
        }
        Account sessionLogin = (Account) session.get("user");
        try {
            Orders order = new Orders();
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setOrderStatus("Chờ xác nhận");
            order.setTotalMoney(BigDecimal.valueOf(totalMoney.get()));
            order.setNote(note.get());
            order.setDeliveryDate(orderHelper.getDeliveryDate());
            order.setPaymentMethod(paymentMethod.get());
            order.setAccountId(sessionLogin.getId());
            order.setIsDeleted(false);
            if (shipDetailId.isPresent()) {
                order.setShipDetailId(Integer.parseInt(shipDetailId.get()));
            } else if (address.isPresent()) {
                // Lưu địa chỉ
                ShipDetail shipDetailSaved = shipDetailRepo.save(new ShipDetail(user.getPhone(), address.get(), sessionLogin.getId(), false));
                order.setShipDetailId(shipDetailSaved.getId());
            }
            //Save đơn hàng
            Orders orderSaved = orderDAO.save(order);
            Integer orderId = orderSaved.getId();
            //Chi tiết đơn hàng
            List<CartDetail> listCartDetail = cartDetailRepo.getCartDetail(cartId.get());
            for (CartDetail item : listCartDetail) {
                OdersDetail ordersDetail = new OdersDetail();
                ordersDetail.setOrderId(orderId);
                ordersDetail.setProductId(item.getProductId());
                ordersDetail.setAmount(item.getAmount());
                Product product = productRepo.findById(item.getProductId()).get();
                ordersDetail.setPrice(BigDecimal.valueOf(product.getPrice()));
                ordersDetail.setDeleted(false);
                //Save chi tiết đơn hàng
                orderDetailDAO.save(ordersDetail);
            }
            cartDetailRepo.deleteAllByCartId(cartId.get());
            return "redirect:/mvc/shopping-cart?orderId=" + orderId + "&orderSaved=true";
        } catch (Exception e) {
            return "redirect:/mvc/shopping-cart?orderSaved=false";
        }
    }
}
