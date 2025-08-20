package com.fashion_store.controller.admin;

import com.fashion_store.dto.importReceipt.request.ImportReceiptRequest;
import com.fashion_store.dto.importReceipt.request.ImportReceiptUpdateRequest;
import com.fashion_store.dto.common.response.ApiResponse;
import com.fashion_store.dto.importReceipt.response.ImportReceiptResponse;
import com.fashion_store.service.ImportReceiptService;
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
@RequestMapping("/api/admin/import-receipt")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImportReceiptController {
    ImportReceiptService importReceiptService;

    @GetMapping
    @PreAuthorize("hasAuthority('IMPORT_RECEPT_VIEW')")
    public ApiResponse<List<ImportReceiptResponse>> getAll(
            @RequestParam(value = "deleted", required = false) boolean deleted
    ) {
        return ApiResponse.<List<ImportReceiptResponse>>builder()
                .result(importReceiptService.getAll(deleted))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('IMPORT_RECEPT_VIEW')")
    public ApiResponse<ImportReceiptResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<ImportReceiptResponse>builder()
                .result(importReceiptService.getInfo(id))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('IMPORT_RECEPT_CREATE')")
    public ApiResponse<ImportReceiptResponse> create(@RequestBody @Valid ImportReceiptRequest request) {
        return ApiResponse.<ImportReceiptResponse>builder()
                .message("Tạo phiếu nhập hàng thành công")
                .result(importReceiptService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('IMPORT_RECEPT_UPDATE')")
    public ApiResponse<ImportReceiptResponse> update(@RequestBody @Valid ImportReceiptUpdateRequest request, @PathVariable Long id) {
        return ApiResponse.<ImportReceiptResponse>builder()
                .message("Cập nhật phiếu nhập hàng thành công")
                .result(importReceiptService.update(request, id))
                .build();
    }

    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasAuthority('IMPORT_RECEPT_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        importReceiptService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục phiếu nhập hàng thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('IMPORT_RECEPT_DELETE')")
    public ApiResponse<ImportReceiptResponse> delete(@PathVariable Long id) {
        importReceiptService.delete(id);
        return ApiResponse.<ImportReceiptResponse>builder()
                .message("Xóa phiếu nhập hàng thành công")
                .build();
    }

    @DeleteMapping("/{id}/destroy")
    @PreAuthorize("hasAuthority('IMPORT_RECEPT_DELETE')")
    public ApiResponse<ImportReceiptResponse> destroy(@PathVariable Long id) {
        importReceiptService.destroy(id);
        return ApiResponse.<ImportReceiptResponse>builder()
                .message("Phiếu nhập hàng đã bị xóa vĩnh viễn")
                .build();
    }
}
