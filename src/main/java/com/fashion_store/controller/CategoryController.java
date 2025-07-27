package com.fashion_store.controller;

import com.fashion_store.dto.request.CategoryRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.CategoryResponse;
import com.fashion_store.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @GetMapping()
    public ApiResponse<List<CategoryResponse>> create() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAll())
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<CategoryResponse> findAll(@RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .message("Tạo danh mục thành công")
                .result(categoryService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<CategoryResponse> findAll(@RequestBody @Valid CategoryRequest request, @PathVariable Long id) {
        return ApiResponse.<CategoryResponse>builder()
                .message("Sửa danh mục thành công")
                .result(categoryService.update(request, id))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<CategoryResponse> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.<CategoryResponse>builder()
                .message("Xóa danh mục thành công")
                .build();
    }
}
