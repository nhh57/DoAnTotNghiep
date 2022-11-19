package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.enums.PaypalPaymentIntent;
import com.example.ecommerce.enums.PaypalPaymentMethod;
import com.example.ecommerce.mvc.common.PAYPAL_URL;
import com.example.ecommerce.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("mvc/order/payment")
public class PaymentController {

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;

    @GetMapping("")
    public String index(){
        return "customer/paypal/index";
    }
    @PostMapping("/pay")
    public String pay(HttpServletRequest request, @RequestParam("price") double price ){
        String cancelUrl = Utils.getBaseURL(request) + "/mvc/order/payment/" + PAYPAL_URL.URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/mvc/order/payment/" + PAYPAL_URL.URL_PAYPAL_SUCCESS;
        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            List<Links> list=payment.getLinks();
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/mvc/order/payment";
    }
    @GetMapping(PAYPAL_URL.URL_PAYPAL_CANCEL)
    public String cancelPay(){
        return "customer/paypal/cancel";
    }
    @GetMapping(PAYPAL_URL.URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){

                return "customer/paypal/success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/mvc/order/payment";
    }
}
