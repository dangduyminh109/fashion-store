package com.fashion_store.controller.admin;

import com.fashion_store.dto.order.request.OrderCreateRequest;
import com.fashion_store.dto.order.request.OrderUpdateRequest;
import com.fashion_store.dto.common.response.ApiResponse;
import com.fashion_store.dto.order.response.OrderResponse;
import com.fashion_store.service.OrderService;
import com.fashion_store.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/order")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;
    PaymentService paymentService;

    @GetMapping
    @PreAuthorize("hasAuthority('ORDER_VIEW')")
    public ApiResponse<List<OrderResponse>> getAll(
            @RequestParam(value = "deleted", required = false) boolean deleted
    ) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAll(deleted))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ORDER_VIEW')")
    public ApiResponse<OrderResponse> getInfo(@PathVariable String id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getInfo(id))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORDER_CREATE')")
    public ApiResponse<OrderResponse> create(@RequestBody @Valid OrderCreateRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .message("Tạo đơn hàng thành công")
                .result(orderService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    public ApiResponse<OrderResponse> update(@RequestBody @Valid OrderUpdateRequest request, @PathVariable String id) {
        return ApiResponse.<OrderResponse>builder()
                .message("Cập nhật đơn hàng thành công")
                .result(orderService.update(request, id))
                .build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    public ApiResponse<Void> status(@PathVariable String id,
                                    @RequestParam("status") String status) {
        orderService.status(id, status);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái đơn hàng thành công")
                .build();
    }

    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable String id) {
        orderService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục đơn hàng thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORDER_DELETE')")
    public ApiResponse<OrderResponse> delete(@PathVariable String id) {
        orderService.delete(id);
        return ApiResponse.<OrderResponse>builder()
                .message("Xóa đơn hàng thành công")
                .build();
    }

    @DeleteMapping("/{id}/destroy")
    @PreAuthorize("hasAuthority('ORDER_DELETE')")
    public ApiResponse<OrderResponse> destroy(@PathVariable String id) {
        orderService.destroy(id);
        return ApiResponse.<OrderResponse>builder()
                .message("Đơn hàng đã bị xóa vĩnh viễn")
                .build();
    }
}
