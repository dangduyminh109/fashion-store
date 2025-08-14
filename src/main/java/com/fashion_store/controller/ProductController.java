package com.fashion_store.controller;

import com.fashion_store.dto.request.ProductCreateRequest;
import com.fashion_store.dto.request.ProductUpdateRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.ProductFromCategoryResponse;
import com.fashion_store.dto.response.ProductResponse;
import com.fashion_store.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @GetMapping("/getAll")
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

    @GetMapping("/info/slug/{slug}")
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

    @GetMapping()
    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    public ApiResponse<List<ProductResponse>> getAll(
            @RequestParam(value = "deleted", required = false) boolean deleted,
            @RequestParam(value = "name", required = false) String name
    ) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAll(deleted, name))
                .build();
    }


    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    public ApiResponse<ProductResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    public ApiResponse<ProductResponse> create(@ModelAttribute @Valid ProductCreateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .message("Tạo sản phẩm thành công")
                .result(productService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ApiResponse<ProductResponse> update(@ModelAttribute @Valid ProductUpdateRequest request, @PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .message("Cập nhật sản phẩm thành công")
                .result(productService.update(request, id))
                .build();
    }

    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        productService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục sản phẩm thành công")
                .build();
    }

    @PatchMapping("/featured/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ApiResponse<Void> featured(@PathVariable Long id) {
        productService.featured(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật sản phẩm nổi bật thành công")
                .build();
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ApiResponse<Void> status(@PathVariable Long id) {
        productService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }

    @PatchMapping("/variant/status/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ApiResponse<Void> statusVariant(@PathVariable Long id) {
        productService.statusVariant(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    public ApiResponse<ProductResponse> delete(@PathVariable Long id) {
        productService.delete(id);
        return ApiResponse.<ProductResponse>builder()
                .message("Xóa sản phẩm thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    public ApiResponse<ProductResponse> destroy(@PathVariable Long id) {
        productService.destroy(id);
        return ApiResponse.<ProductResponse>builder()
                .message("Sản phẩm đã bị xóa vĩnh viễn")
                .build();
    }
}
