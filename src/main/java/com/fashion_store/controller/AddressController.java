package com.fashion_store.controller;

import com.fashion_store.dto.request.AddressRequest;
import com.fashion_store.dto.request.AddressUpdateRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.AddressResponse;
import com.fashion_store.service.AddressService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/address")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressController {
    AddressService addressService;

    @GetMapping("/info/{id}")
    public ApiResponse<AddressResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<AddressResponse>builder()
                .result(addressService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<AddressResponse> create(@RequestBody @Valid AddressRequest request) {
        return ApiResponse.<AddressResponse>builder()
                .message("Tạo địa chỉ thành công")
                .result(addressService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<AddressResponse> update(@RequestBody @Valid AddressUpdateRequest request, @PathVariable Long id) {
        return ApiResponse.<AddressResponse>builder()
                .message("Cập nhật địa chỉ thành công")
                .result(addressService.update(request, id))
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    public ApiResponse<AddressResponse> destroy(@PathVariable Long id) {
        addressService.destroy(id);
        return ApiResponse.<AddressResponse>builder()
                .message("Địa chỉ đã bị xóa vĩnh viễn")
                .build();
    }
}
