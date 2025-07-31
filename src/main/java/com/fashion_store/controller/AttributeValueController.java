package com.fashion_store.controller;

import com.fashion_store.dto.request.AttributeValueRequest;
import com.fashion_store.dto.request.AttributeValueUpdateRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.AttributeValueResponse;
import com.fashion_store.service.AttributeValueService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/attribute-value")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeValueController {
    AttributeValueService attributeValueService;

    @GetMapping()
    public ApiResponse<List<AttributeValueResponse>> getAll() {
        return ApiResponse.<List<AttributeValueResponse>>builder()
                .result(attributeValueService.getAll())
                .build();
    }

    @GetMapping("/info/{id}")
    public ApiResponse<AttributeValueResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<AttributeValueResponse>builder()
                .result(attributeValueService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<AttributeValueResponse> create(@ModelAttribute @Valid AttributeValueRequest request) {
        return ApiResponse.<AttributeValueResponse>builder()
                .message("Tạo giá trị của thuộc tính thành công")
                .result(attributeValueService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<AttributeValueResponse> update(@ModelAttribute @Valid AttributeValueUpdateRequest request, @PathVariable Long id) {
        return ApiResponse.<AttributeValueResponse>builder()
                .message("Cập nhật giá trị của thuộc tính thành công")
                .result(attributeValueService.update(request, id))
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    public ApiResponse<AttributeValueResponse> destroy(@PathVariable Long id) {
        attributeValueService.destroy(id);
        return ApiResponse.<AttributeValueResponse>builder()
                .message("Giá trị của thuộc tính đã bị xóa vĩnh viễn")
                .build();
    }
}
