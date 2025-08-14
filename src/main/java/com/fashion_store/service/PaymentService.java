package com.fashion_store.service;

import com.fashion_store.Utils.SecurityUtils;
import com.fashion_store.dto.request.OrderCreateRequest;
import com.fashion_store.entity.Order;
import com.fashion_store.enums.AuthProvider;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    OrderService orderService;
    OrderRepository orderRepository;
    @NonFinal
    @Value("${vnpay.tmnCode}")
    String TMN_CODE;
    @NonFinal
    @Value("${vnpay.hashSecret}")
    String SECRET_KEY;
    @NonFinal
    @Value("${vnpay.returnUrl}")
    String RETURN_URL;
    @NonFinal
    @Value("${vnpay.payUrl}")
    String PAY_URL;

    public String createVNPayPaymentFromId(String OrderId, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        String customerId = SecurityUtils.getCurrentUserId();
        Order order = orderRepository.findById(OrderId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        if (!order.getCustomer().getAuthProvider().equals(AuthProvider.GUEST)
                && !order.getCustomer().getId().equals(customerId)
        )
            throw new AppException(ErrorCode.NOT_AUTHORIZED_ORDER_ACCESS);

        if (order.getIsPaid())
            throw new AppException(ErrorCode.PAID_ORDER);

        String transactionRef = getRandomNumber(8);
        order.setTransactionRef(transactionRef);
        orderRepository.save(order);
        return createPaymentUrl(order.getId(), order.getFinalAmount(), httpServletRequest, transactionRef);
    }

    public String createVNPayPayment(OrderCreateRequest request, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        String customerId = SecurityUtils.getCurrentUserId();
        Order order = orderService.createOrder(request, customerId);
        String transactionRef = getRandomNumber(8);
        order.setTransactionRef(transactionRef);
        orderRepository.save(order);
        return createPaymentUrl(order.getId(), order.getFinalAmount(), httpServletRequest, transactionRef);
    }

    public boolean handleVNPayReturn(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String, String> fields = getFieldsFromRequest(request);
        String vnp_SecureHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        String signData = hashAllFields(fields);
        String computedHash = hmacSHA512(SECRET_KEY, signData);
        if (computedHash.equals(vnp_SecureHash)) {
            String responseCode = fields.get("vnp_ResponseCode");
            String transactionRef = fields.get("vnp_TxnRef");
            if ("00".equals(responseCode)) {
                Order order = orderRepository.findByTransactionRef(transactionRef)
                        .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
                order.setIsPaid(true);
                order.setPaidAt(LocalDateTime.now());
                orderRepository.save(order);
                return true;
            }
        }
        return false;
    }

    private String createPaymentUrl(String orderId, BigDecimal amount, HttpServletRequest httpServletRequest, String transactionRef) throws UnsupportedEncodingException {
        String vnp_TxnRef = transactionRef;
        String vnp_OrderInfo = "Thanh toan don hang " + orderId;
        String vnp_TmnCode = TMN_CODE;
        String vnp_HashSecret = SECRET_KEY;
        String vnp_ReturnUrl = RETURN_URL;
        String vnp_IpAddr = getIpAddress(httpServletRequest);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String vnp_CreateDate = formatter.format(new Date());

        BigDecimal vnpAmount = amount.multiply(new BigDecimal(100));
        String vnp_Amount = vnpAmount.toBigInteger().toString(); // VNPAY tính theo đơn vị đồng

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_OrderType", "billpayment");

        // Sắp xếp và tạo query + hash
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
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PAY_URL + "?" + queryUrl;
        return paymentUrl;
    }

    private Map<String, String> getFieldsFromRequest(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            fields.put(paramName, paramValue);
        }
        return fields;
    }

    private String hashAllFields(Map<String, String> fields) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = fields.get(key);
            // Encode value giống như VNPay làm
            sb.append(key).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
            if (i < fieldNames.size() - 1) {
                sb.append('&');
            }
        }
        return sb.toString();
    }


    private String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec SECRET_KEY = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(SECRET_KEY);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    private String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || ipAddress.isEmpty()) {
                ipAddress = request.getRemoteAddr();
            }
            if ("0:0:0:0:0:0:0:1".equals(ipAddress)) {
                ipAddress = "127.0.0.1";
            }
        } catch (Exception e) {
            ipAddress = "127.0.0.1";
        }
        return ipAddress;
    }

    private String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
