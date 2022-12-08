package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.config.VNPayConfig;
import com.example.ecommerce.enums.PaypalPaymentIntent;
import com.example.ecommerce.enums.PaypalPaymentMethod;
import com.example.ecommerce.model.*;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.model.helper.OrderHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

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
    SessionDAO session;
    @Autowired
    CartDetailRepo cartDetailRepo;

    @Autowired
    ProductRepo productDAO;

    @Autowired
    WarehouseRepo warehouseDAO;
    OrderHelper orderHelper = new OrderHelper();

    @PostMapping("pay")
    public String pay(HttpServletRequest request,
                      @RequestParam("shipMethod") Optional<String> shipMethod,
                      @RequestParam("shipMethodId") Optional<Integer> shipMethodId,
                      @RequestParam("deliveryCharges") Optional<Integer> deliveryCharges,
                      @RequestParam("total-money") Optional<Double> totalMoney,
                      @RequestParam("cart-id") Optional<Integer> cartId,
                      @RequestParam("ship-detail-id") Optional<String> shipDetailId,
                      @RequestParam("payment-method") Optional<String> paymentMethod,
                      @RequestParam("note") Optional<String> note) throws PayPalRESTException, UnsupportedEncodingException {
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
        order.setDeliveryCharges(deliveryCharges.get());
        order.setShipMethodId(shipMethodId.get());
        order.setShipMethod(shipMethod.get());
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
            Product product = productDAO.findById(item.getProductId()).get();
            product.setNumberOfSale(product.getNumberOfSale() + item.getAmount());
            productDAO.save(product);
            Warehouse warehouse = warehouseDAO.findByProductId(item.getProductId());
            warehouse.setAmount(warehouse.getAmount() - item.getAmount());
            warehouseDAO.save(warehouse);
            OdersDetail ordersDetail = new OdersDetail();
            ordersDetail.setOrderId(orderId);
            ordersDetail.setProductId(item.getProductId());
            ordersDetail.setAmount(item.getAmount());
            ordersDetail.setPrice(BigDecimal.valueOf(product.getPrice()));
            ordersDetail.setDeleted(false);
            //Save chi tiết đơn hàng
            orderDetailDAO.save(ordersDetail);
        }
        cartDetailRepo.deleteAllByCartId(cartId.get());
        if (paymentMethod.get().equalsIgnoreCase("Paypal")) {
            String urlReturn = Utils.getBaseURL(request) + "/mvc/order/payment/paypal/status?orderId=" + orderId;
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
        } else if(paymentMethod.get().equalsIgnoreCase("VNPay")){
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
            String vnp_IpAddr = VNPayConfig.getIpAddress(request);
            double totalMoneyDouble=totalMoney.get();
            int amount = (int)totalMoneyDouble * 100;
            Map vnp_Params = new HashMap<>();
            //Phiên bản api mà merchant kết nối. Phiên bản hiện tại là : 2.1.0
            vnp_Params.put("vnp_Version", vnp_Version);
            //Mã API sử dụng, mã cho giao dịch thanh toán là: pay
            vnp_Params.put("vnp_Command", vnp_Command);
            //Mã website của merchant trên hệ thống của VNPAY. Mã được gửi về email
            vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
            //Số tiền thanh toán * 100 đổi sang string. ví dụ 10,000đ, gửi 1000000
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            //Đơn vị tiền tệ sử dụng thanh toán. Hiện tại chỉ hỗ trợ VND
            vnp_Params.put("vnp_CurrCode", "VND");
//        // Mã phương thức thanh toán, mã loại ngân hàng hoặc ví điện tử thanh toán. Ví dụ:VNPAYQR,VNBANK (Nội địa), INTCARD (QT)
//        vnp_Params.put("vnp_BankCode", bank_code);
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            // vnp_OrderInfo là nội dung ck, ví dụ: Nap tien cho thue bao 0123456789. So tien 100,000 VND
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang TIMESHOP");

            //Mã danh mục hàng hóa. Mỗi hàng hóa sẽ thuộc một nhóm danh mục do VNPAY quy định. Xem thêm bảng Danh mục hàng hóa
            // https://sandbox.vnpayment.vn/apis/docs/loai-hang-hoa/
            // 200000 là mã thời trang
            vnp_Params.put("vnp_OrderType", "200000");

            //Ngôn ngữ giao diện: vn hoặc en
            vnp_Params.put("vnp_Locale", "vn");

            //URL thông báo kết quả giao dịch khi Khách hàng kết thúc thanh toán
            vnp_Params.put("vnp_ReturnUrl", VNPayConfig.getUrlReturn(request,orderId));

            //Địa chỉ IP của khách hàng thực hiện giao dịch. Ví dụ: 13.160.92.202
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            //Là thời gian phát sinh giao dịch định dạng yyyyMMddHHmmss (Time zone GMT+7) Ví dụ: 20220101103111
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            //Thời gian hết hạn thanh toán GMT+7, định dạng: yyyyMMddHHmmss, ví dụ ở đây 15 phút
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            //Build data to hash and querystring
            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
            return "redirect:"+paymentUrl;
        }
        return "redirect:/mvc/order/payment/paypal/status?orderId=" + orderId;
    }

    @GetMapping("paypal/status")
    public String successPay(Model model,
                             @RequestParam("paymentId") Optional<String> paymentId,
                             @RequestParam("PayerID") Optional<String> payerId,
                             @RequestParam Integer orderId) {
        Account sessionLogin = (Account) session.get("user");
        if (sessionLogin == null) {
            return "redirect:/mvc/login?error=errorNoLogin&urlReturn=order/payment/paypal/status?orderId=" + orderId;
        } else if (orderDAO.existByAccountIdAndOrderId(sessionLogin.getId(), orderId) == null) {
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
            return "customer/status";
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/mvc/shop";
    }

    @GetMapping("vn-pay/status")
    public String vnPayStatus(Model model,
                              @RequestParam Integer orderId,
                              @RequestParam("vnp_ResponseCode") Optional<String> vnpResponseCode,
                              @RequestParam("vnp_TransactionStatus") Optional<String> vnpTransactionStatus){
        Account sessionLogin = (Account) session.get("user");
        if (sessionLogin == null) {
            return "redirect:/mvc/login?error=errorNoLogin&urlReturn=order/payment/paypal/status?orderId=" + orderId;
        } else if (orderDAO.existByAccountIdAndOrderId(sessionLogin.getId(), orderId) == null) {
            return "customer/404";
        }
        if(vnpResponseCode.isPresent() && vnpTransactionStatus.isPresent()){
            if(vnpResponseCode.get().equals("00") && vnpTransactionStatus.get().equals("00")){
                Orders orders = orderDAO.findById(orderId).get();
                orders.setPaymentStatus("Đã thanh toán");
                orderDAO.save(orders);
            }
        }
        model.addAttribute("order", orderDAO.findById(orderId).get());
        model.addAttribute("orderDetail", orderDetailDAO.findAllByOrderId(orderId));
        return "customer/status";
    }
}
