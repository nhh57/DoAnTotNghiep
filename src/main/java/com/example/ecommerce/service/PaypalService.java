package com.example.ecommerce.service;

import com.example.ecommerce.enums.PaypalPaymentIntent;
import com.example.ecommerce.enums.PaypalPaymentMethod;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {
    public Payment createPayment(
            Double total,
            String currency,
            PaypalPaymentMethod method,
            PaypalPaymentIntent intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException;
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
