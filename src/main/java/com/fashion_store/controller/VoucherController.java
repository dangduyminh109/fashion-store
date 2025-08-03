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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/voucher")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherController {
    VoucherService voucherService;

    @GetMapping()
    public ApiResponse<List<VoucherResponse>> getAll() {
        return ApiResponse.<List<VoucherResponse>>builder()
                .result(voucherService.getAll())
                .build();
    }

    @GetMapping("/info/{id}")
    public ApiResponse<VoucherResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<VoucherResponse>builder()
                .result(voucherService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<VoucherResponse> create(@RequestBody @Valid VoucherRequest request) {
        return ApiResponse.<VoucherResponse>builder()
                .message("Tạo voucher thành công")
                .result(voucherService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<VoucherResponse> update(@RequestBody @Valid VoucherUpdateRequest request, @PathVariable Long id) {
        return ApiResponse.<VoucherResponse>builder()
                .message("Cập nhật voucher thành công")
                .result(voucherService.update(request, id))
                .build();
    }

    @PatchMapping("/restore/{id}")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        voucherService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục voucher thành công")
                .build();
    }

    @PatchMapping("/status/{id}")
    public ApiResponse<Void> status(@PathVariable Long id) {
        voucherService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }


    @DeleteMapping("/delete/{id}")
    public ApiResponse<VoucherResponse> delete(@PathVariable Long id) {
        voucherService.delete(id);
        return ApiResponse.<VoucherResponse>builder()
                .message("Xóa voucher thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    public ApiResponse<VoucherResponse> destroy(@PathVariable Long id) {
        voucherService.destroy(id);
        return ApiResponse.<VoucherResponse>builder()
                .message("Voucher đã bị xóa vĩnh viễn")
                .build();
    }
}
