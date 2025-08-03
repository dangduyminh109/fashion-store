package com.fashion_store.controller;

import com.fashion_store.dto.request.ProductCreateRequest;
import com.fashion_store.dto.request.ProductUpdateRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.ProductResponse;
import com.fashion_store.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @GetMapping()
    public ApiResponse<List<ProductResponse>> getAll() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAll())
                .build();
    }

    @GetMapping("/info/{id}")
    public ApiResponse<ProductResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<ProductResponse> create(@ModelAttribute @Valid ProductCreateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .message("Tạo sản phẩm thành công")
                .result(productService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<ProductResponse> update(@ModelAttribute @Valid ProductUpdateRequest request, @PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .message("Cập nhật sản phẩm thành công")
                .result(productService.update(request, id))
                .build();
    }

    @PatchMapping("/restore/{id}")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        productService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục sản phẩm thành công")
                .build();
    }

    @PatchMapping("/featured/{id}")
    public ApiResponse<Void> featured(@PathVariable Long id) {
        productService.featured(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật sản phẩm nổi bật thành công")
                .build();
    }

    @PatchMapping("/status/{id}")
    public ApiResponse<Void> status(@PathVariable Long id) {
        productService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }


    @DeleteMapping("/delete/{id}")
    public ApiResponse<ProductResponse> delete(@PathVariable Long id) {
        productService.delete(id);
        return ApiResponse.<ProductResponse>builder()
                .message("Xóa sản phẩm thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    public ApiResponse<ProductResponse> destroy(@PathVariable Long id) {
        productService.destroy(id);
        return ApiResponse.<ProductResponse>builder()
                .message("Sản phẩm đã bị xóa vĩnh viễn")
                .build();
    }
}
