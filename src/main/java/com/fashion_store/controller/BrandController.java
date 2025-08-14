package com.fashion_store.controller;

import com.fashion_store.dto.request.BrandRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.BrandResponse;
import com.fashion_store.service.BrandService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/brand")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandService brandService;

    @GetMapping()
    @PreAuthorize("hasAuthority('BRAND_VIEW')")
    public ApiResponse<List<BrandResponse>> getAll(
            @RequestParam(value = "deleted", required = false) boolean deleted
    ) {
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.getAll(deleted))
                .build();
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('BRAND_VIEW')")
    public ApiResponse<BrandResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('BRAND_CREATE')")
    public ApiResponse<BrandResponse> create(@ModelAttribute @Valid BrandRequest request) {
        return ApiResponse.<BrandResponse>builder()
                .message("Tạo thương hiệu thành công")
                .result(brandService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('BRAND_UPDATE')")
    public ApiResponse<BrandResponse> update(@ModelAttribute @Valid BrandRequest request, @PathVariable Long id) {
        return ApiResponse.<BrandResponse>builder()
                .message("Cập nhật thương hiệu thành công")
                .result(brandService.update(request, id))
                .build();
    }

    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('BRAND_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        brandService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục thương hiệu thành công")
                .build();
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAuthority('BRAND_UPDATE')")
    public ApiResponse<Void> status(@PathVariable Long id) {
        brandService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('BRAND_DELETE')")
    public ApiResponse<BrandResponse> delete(@PathVariable Long id) {
        brandService.delete(id);
        return ApiResponse.<BrandResponse>builder()
                .message("Xóa thương hiệu thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    @PreAuthorize("hasAuthority('BRAND_DELETE')")
    public ApiResponse<BrandResponse> destroy(@PathVariable Long id) {
        brandService.destroy(id);
        return ApiResponse.<BrandResponse>builder()
                .message("Thương hiệu đã bị xóa vĩnh viễn")
                .build();
    }
}
