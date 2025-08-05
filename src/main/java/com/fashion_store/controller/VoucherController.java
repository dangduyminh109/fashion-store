package com.fashion_store.controller;

import com.fashion_store.dto.request.VoucherRequest;
import com.fashion_store.dto.request.VoucherUpdateRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.VoucherResponse;
import com.fashion_store.service.VoucherService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/voucher")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherController {
    VoucherService voucherService;

    @GetMapping()
    @PreAuthorize("hasAuthority('VOUCHER_VIEW')")
    public ApiResponse<List<VoucherResponse>> getAll() {
        return ApiResponse.<List<VoucherResponse>>builder()
                .result(voucherService.getAll())
                .build();
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('VOUCHER_VIEW')")
    public ApiResponse<VoucherResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<VoucherResponse>builder()
                .result(voucherService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('VOUCHER_CREATE')")
    public ApiResponse<VoucherResponse> create(@RequestBody @Valid VoucherRequest request) {
        return ApiResponse.<VoucherResponse>builder()
                .message("Tạo voucher thành công")
                .result(voucherService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('VOUCHER_UPDATE')")
    public ApiResponse<VoucherResponse> update(@RequestBody @Valid VoucherUpdateRequest request, @PathVariable Long id) {
        return ApiResponse.<VoucherResponse>builder()
                .message("Cập nhật voucher thành công")
                .result(voucherService.update(request, id))
                .build();
    }

    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('VOUCHER_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        voucherService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục voucher thành công")
                .build();
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAuthority('VOUCHER_UPDATE')")
    public ApiResponse<Void> status(@PathVariable Long id) {
        voucherService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('VOUCHER_DELETE')")
    public ApiResponse<VoucherResponse> delete(@PathVariable Long id) {
        voucherService.delete(id);
        return ApiResponse.<VoucherResponse>builder()
                .message("Xóa voucher thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    @PreAuthorize("hasAuthority('VOUCHER_DELETE')")
    public ApiResponse<VoucherResponse> destroy(@PathVariable Long id) {
        voucherService.destroy(id);
        return ApiResponse.<VoucherResponse>builder()
                .message("Voucher đã bị xóa vĩnh viễn")
                .build();
    }
}
