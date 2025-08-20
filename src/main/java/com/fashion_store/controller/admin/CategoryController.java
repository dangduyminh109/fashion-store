package com.fashion_store.controller.admin;

import com.fashion_store.dto.category.request.CategoryRequest;
import com.fashion_store.dto.common.response.ApiResponse;
import com.fashion_store.dto.category.response.CategoryResponse;
import com.fashion_store.dto.category.response.CategoryTreeResponse;
import com.fashion_store.service.CategoryService;
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
@RequestMapping("/api/admin/category")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    public ApiResponse<List<CategoryResponse>> getAll(
            @RequestParam(value = "deleted", required = false) boolean deleted
    ) {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAll(deleted))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    public ApiResponse<CategoryResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getInfo(id))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    public ApiResponse<CategoryResponse> create(@ModelAttribute @Valid CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .message("Tạo danh mục thành công")
                .result(categoryService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    public ApiResponse<CategoryResponse> update(@ModelAttribute @Valid CategoryRequest request, @PathVariable Long id) {
        return ApiResponse.<CategoryResponse>builder()
                .message("Cập nhật danh mục thành công")
                .result(categoryService.update(request, id))
                .build();
    }

    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        categoryService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục danh mục thành công")
                .build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    public ApiResponse<Void> status(@PathVariable Long id) {
        categoryService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    public ApiResponse<CategoryResponse> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.<CategoryResponse>builder()
                .message("Xóa danh mục thành công")
                .build();
    }

    @DeleteMapping("/{id}/destroy")
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    public ApiResponse<CategoryResponse> destroy(@PathVariable Long id) {
        categoryService.destroy(id);
        return ApiResponse.<CategoryResponse>builder()
                .message("Danh mục đã bị xóa vĩnh viễn")
                .build();
    }
}
