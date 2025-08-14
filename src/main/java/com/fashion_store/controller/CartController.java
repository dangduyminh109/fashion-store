package com.fashion_store.controller;

import com.fashion_store.dto.request.CartItemRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.CartItemResponse;
import com.fashion_store.service.CartService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/cart")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;

    @GetMapping()
    public ApiResponse<List<CartItemResponse>> getCart() {
        return ApiResponse.<List<CartItemResponse>>builder()
                .result(cartService.getCart())
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<CartItemResponse> add(@RequestBody CartItemRequest cartItemRequest) {
        return ApiResponse.<CartItemResponse>builder()
                .result(cartService.add(cartItemRequest))
                .build();
    }

    @PutMapping("/update")
    public ApiResponse<CartItemResponse> update(@RequestBody CartItemRequest cartItemRequest) {
        return ApiResponse.<CartItemResponse>builder()
                .result(cartService.update(cartItemRequest))
                .build();
    }

    @DeleteMapping("/delete/{variantId}")
    public ApiResponse<Void> delete(@PathVariable Long variantId) {
        cartService.delete(variantId);
        return ApiResponse.<Void>builder()
                .message("Xóa sản phẩm khỏi giỏ hàng thành công")
                .build();
    }
}
