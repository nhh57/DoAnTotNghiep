package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.enums.PaypalPaymentIntent;
import com.example.ecommerce.enums.PaypalPaymentMethod;
import com.example.ecommerce.model.*;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.model.helper.OrderHelper;
import com.example.ecommerce.mvc.common.PAYPAL_URL;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/order/payment")
public class PaymentController {

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;

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
    ShoppingCartDAO shoppingCartDAO;
    @Autowired
    CartDetailRepo cartDetailRepo;

    @Autowired
    ProductRepo productDAO;

    OrderHelper orderHelper = new OrderHelper();
    CartHelper cartHelper = new CartHelper();

    @GetMapping("")
    public String index() {
        return "customer/paypal/index";
    }

    @PostMapping("/pay")
    public String pay(HttpServletRequest request,
                      @RequestParam("total-money") Optional<Double> totalMoney,
                      @RequestParam("cart-id") Optional<Integer> cartId,
                      @RequestParam("ship-detail-id") Optional<String> shipDetailId,
                      @RequestParam("payment-method") Optional<String> paymentMethod,
                      @RequestParam("note") Optional<String> note) throws PayPalRESTException {
        if (!shipDetailId.isPresent()) {
            return "redirect:/mvc/order?addressNull=true";
        }
        if (!paymentMethod.isPresent()) {
            return "redirect:/mvc/order?paymentNull=true";
        }
        Account sessionLogin = (Account) session.get("user");
        Orders order = new Orders();
        order.setOrderDate(new Date());
        order.setOrderStatus("Chờ xác nhận");
        order.setTotalMoney(BigDecimal.valueOf(totalMoney.get()));
        order.setNote(note.get());
        order.setDeliveryDate(orderHelper.getDeliveryDate());
        order.setPaymentMethod(paymentMethod.get());
        order.setPaymentStatus("Chưa thanh toán");
        order.setAccountId(sessionLogin.getId());
        order.setIsDeleted(false);
        if (shipDetailId.isPresent()) {
            order.setShipDetailId(Integer.parseInt(shipDetailId.get()));
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
        if (paymentMethod.get().equalsIgnoreCase("Paypal")) {
            String urlReturn = Utils.getBaseURL(request) + "/mvc/order/payment/" + PAYPAL_URL.URL_PAYPAL + "?orderId=" + orderId;
            Double total = Utils.round(totalMoney.get() / 24000, 2);
            Payment payment = paypalService.createPayment(
                    total,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    urlReturn,
                    urlReturn);
            List<Links> list = payment.getLinks();
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        }
        return "redirect:/mvc/order/payment/pay/status?orderId="+orderId;
    }

    @GetMapping(PAYPAL_URL.URL_PAYPAL)
    public String successPay(Model model,
                             @RequestParam("paymentId") Optional<String> paymentId,
                             @RequestParam("PayerID") Optional<String> payerId,
                             @RequestParam Integer orderId) {
        Account sessionLogin = (Account) session.get("user");
        if(sessionLogin==null){
            return "redirect:/mvc/login?error=errorNoLogin&urlReturn=order/payment/pay/status?orderId="+orderId;
        }else if(orderDAO.existByAccountIdAndOrderId(sessionLogin.getId(),orderId)==null){
            return "customer/404";
        }
        try {
            if (payerId.isPresent() && payerId.isPresent()) {
                Payment payment = paypalService.executePayment(paymentId.get(), payerId.get());
                if (payment.getState().equals("approved")) {
                    Orders orders = orderDAO.findById(orderId).get();
                    orders.setPaymentStatus("Đã thanh toán");
                    orderDAO.save(orders);
                }
            }
            model.addAttribute("order", orderDAO.findById(orderId).get());
            model.addAttribute("orderDetail", orderDetailDAO.findAllByOrderId(orderId));
            return "customer/paypal/status";
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/mvc/shop";
    }
}
