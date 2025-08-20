package com.fashion_store.controller.client;

import com.fashion_store.dto.common.response.ApiResponse;
import com.fashion_store.dto.product.response.ProductFromCategoryResponse;
import com.fashion_store.dto.product.response.ProductResponse;
import com.fashion_store.service.ProductService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/product")
@RestController("ClientProductController")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProduct())
                .build();
    }

    @GetMapping("/category/{slug}")
    public ApiResponse<ProductFromCategoryResponse> getByCategory(@PathVariable String slug) {
        return ApiResponse.<ProductFromCategoryResponse>builder()
                .result(productService.getByCategory(slug))
                .build();
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<ProductResponse> getInfoBySlug(@PathVariable String slug) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getInfoBySlug(slug))
                .build();
    }

    @GetMapping("/variant/{id}")
    public ApiResponse<ProductResponse> getVariant(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getVariant(id))
                .build();
    }
}
