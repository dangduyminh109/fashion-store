package com.fashion_store.controller;

import com.fashion_store.dto.request.SupplierRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.SupplierResponse;
import com.fashion_store.service.SupplierService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/supplier")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierController {
    SupplierService supplierService;

    @GetMapping()
    @PreAuthorize("hasAuthority('SUPPLIER_VIEW')")
    public ApiResponse<List<SupplierResponse>> getAll(
            @RequestParam(value = "deleted", required = false) boolean deleted
    ) {
        return ApiResponse.<List<SupplierResponse>>builder()
                .result(supplierService.getAll(deleted))
                .build();
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER_VIEW')")
    public ApiResponse<SupplierResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<SupplierResponse>builder()
                .result(supplierService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SUPPLIER_CREATE')")
    public ApiResponse<SupplierResponse> create(@RequestBody @Valid SupplierRequest request) {
        return ApiResponse.<SupplierResponse>builder()
                .message("Tạo nhà cung cấp thành công")
                .result(supplierService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER_UPDATE')")
    public ApiResponse<SupplierResponse> update(@RequestBody @Valid SupplierRequest request, @PathVariable Long id) {
        return ApiResponse.<SupplierResponse>builder()
                .message("Cập nhật nhà cung cấp thành công")
                .result(supplierService.update(request, id))
                .build();
    }

    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        supplierService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục nhà cung cấp thành công")
                .build();
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER_UPDATE')")
    public ApiResponse<Void> status(@PathVariable Long id) {
        supplierService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER_DELETE')")
    public ApiResponse<SupplierResponse> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ApiResponse.<SupplierResponse>builder()
                .message("Xóa nhà cung cấp thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    @PreAuthorize("hasAuthority('SUPPLIER_DELETE')")
    public ApiResponse<SupplierResponse> destroy(@PathVariable Long id) {
        supplierService.destroy(id);
        return ApiResponse.<SupplierResponse>builder()
                .message("Nhà cung cấp đã bị xóa vĩnh viễn")
                .build();
    }
}
