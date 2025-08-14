package com.fashion_store.controller;

import com.fashion_store.dto.request.OrderCreateRequest;
import com.fashion_store.dto.request.OrderUpdateRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.OrderResponse;
import com.fashion_store.enums.PaymentMethod;
import com.fashion_store.service.OrderService;
import com.fashion_store.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;
    PaymentService paymentService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ORDER_VIEW')")
    public ApiResponse<List<OrderResponse>> getAll(
            @RequestParam(value = "deleted", required = false) boolean deleted
    ) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAll(deleted))
                .build();
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ORDER_VIEW')")
    public ApiResponse<OrderResponse> getInfo(@PathVariable String id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ORDER_CREATE')")
    public ApiResponse<OrderResponse> create(@RequestBody @Valid OrderCreateRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .message("Tạo đơn hàng thành công")
                .result(orderService.create(request))
                .build();
    }

    @PostMapping("/client/create")
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

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    public ApiResponse<OrderResponse> update(@RequestBody @Valid OrderUpdateRequest request, @PathVariable String id) {
        return ApiResponse.<OrderResponse>builder()
                .message("Cập nhật đơn hàng thành công")
                .result(orderService.update(request, id))
                .build();
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    public ApiResponse<Void> status(@PathVariable String id,
                                    @RequestParam("status") String status) {
        orderService.status(id, status);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái đơn hàng thành công")
                .build();
    }

    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable String id) {
        orderService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục đơn hàng thành công")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    public ApiResponse<OrderResponse> delete(@PathVariable String id) {
        orderService.delete(id);
        return ApiResponse.<OrderResponse>builder()
                .message("Xóa đơn hàng thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    @PreAuthorize("hasAuthority('ORDER_DELETE')")
    public ApiResponse<OrderResponse> destroy(@PathVariable String id) {
        orderService.destroy(id);
        return ApiResponse.<OrderResponse>builder()
                .message("Đơn hàng đã bị xóa vĩnh viễn")
                .build();
    }
}
