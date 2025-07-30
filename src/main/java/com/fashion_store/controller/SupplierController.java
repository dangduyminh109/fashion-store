package com.fashion_store.controller;

import com.fashion_store.dto.request.SupplierRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.SupplierResponse;
import com.fashion_store.service.SupplierService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/supplier")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierController {
    SupplierService supplierService;

    @GetMapping()
    public ApiResponse<List<SupplierResponse>> getAll() {
        return ApiResponse.<List<SupplierResponse>>builder()
                .result(supplierService.getAll())
                .build();
    }

    @GetMapping("/info/{id}")
    public ApiResponse<SupplierResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<SupplierResponse> create(@RequestBody @Valid SupplierRequest request) {
        return ApiResponse.<SupplierResponse>builder()
                .message("Tạo nhà cung cấp thành công")
                .result(supplierService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<SupplierResponse> update(@RequestBody @Valid SupplierRequest request, @PathVariable Long id) {
        return ApiResponse.<SupplierResponse>builder()
                .message("Cập nhật nhà cung cấp thành công")
                .result(supplierService.update(request, id))
                .build();
    }

    @PatchMapping("/restore/{id}")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        supplierService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục nhà cung cấp thành công")
                .build();
    }

    @PatchMapping("/status/{id}")
    public ApiResponse<Void> status(@PathVariable Long id) {
        supplierService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }


    @DeleteMapping("/delete/{id}")
    public ApiResponse<SupplierResponse> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ApiResponse.<SupplierResponse>builder()
                .message("Xóa nhà cung cấp thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    public ApiResponse<SupplierResponse> destroy(@PathVariable Long id) {
        supplierService.destroy(id);
        return ApiResponse.<SupplierResponse>builder()
                .message("Nhà cung cấp đã bị xóa vĩnh viễn")
                .build();
    }
}
