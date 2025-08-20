package com.fashion_store.controller.client;

import com.fashion_store.dto.common.response.ApiResponse;
import com.fashion_store.dto.order.request.OrderCreateRequest;
import com.fashion_store.dto.order.response.OrderResponse;
import com.fashion_store.enums.PaymentMethod;
import com.fashion_store.service.OrderService;
import com.fashion_store.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@AllArgsConstructor
@RequestMapping("/api/order")
@RestController("ClientOrderController")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;
    PaymentService paymentService;

    @PostMapping
    public ApiResponse<?> createClient(@RequestBody @Valid OrderCreateRequest request, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase().trim());
        if (paymentMethod.equals(PaymentMethod.BANK)) {
            return ApiResponse.<String>builder()
                    .message("Tạo đơn hàng thành công")
                    .result(paymentService.createVNPayPayment(request, httpServletRequest))
                    .build();
        }
        return ApiResponse.<OrderResponse>builder()
                .message("Tạo đơn hàng thành công")
                .result(orderService.createClient(request))
                .build();
    }
}
