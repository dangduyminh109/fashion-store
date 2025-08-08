package com.fashion_store.controller;

import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
@RequestMapping("/payment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class paymentController {
    PaymentService paymentService;

    @PostMapping("/{OrderId}")
    public ApiResponse<String> createVNPayPayment(@PathVariable String OrderId, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        return ApiResponse.<String>builder()
                .result(paymentService.createVNPayPaymentFromId(OrderId, httpServletRequest))
                .build();
    }

    @GetMapping("/vnpay_return")
    public void handleVNPayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isSuccess = paymentService.handleVNPayReturn(request, response);
        if (isSuccess)
            response.sendRedirect("http://localhost:5500/test2/payment-success.html");
        else
            response.sendRedirect("http://localhost:5500/test2/payment-error.html");
    }
}
