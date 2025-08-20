package com.fashion_store.controller.client;

import com.fashion_store.dto.address.request.AddressRequest;
import com.fashion_store.dto.address.request.AddressUpdateRequest;
import com.fashion_store.dto.common.response.ApiResponse;
import com.fashion_store.dto.address.response.AddressResponse;
import com.fashion_store.service.AddressService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/address")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressController {
    AddressService addressService;

    @GetMapping("/{id}")
    public ApiResponse<AddressResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<AddressResponse>builder()
                .result(addressService.getInfo(id))
                .build();
    }

    @PostMapping
    public ApiResponse<AddressResponse> create(@RequestBody @Valid AddressRequest request) {
        return ApiResponse.<AddressResponse>builder()
                .message("Tạo địa chỉ thành công")
                .result(addressService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AddressResponse> update(@RequestBody @Valid AddressUpdateRequest request, @PathVariable Long id) {
        return ApiResponse.<AddressResponse>builder()
                .message("Cập nhật địa chỉ thành công")
                .result(addressService.update(request, id))
                .build();
    }

    @DeleteMapping("/{id}/destroy")
    public ApiResponse<AddressResponse> destroy(@PathVariable Long id) {
        addressService.destroy(id);
        return ApiResponse.<AddressResponse>builder()
                .message("Địa chỉ đã bị xóa vĩnh viễn")
                .build();
    }

    @GetMapping("/provinces")
    public String getProvinces() {
        return addressService.getProvinces();
    }

    @GetMapping("/districts/{provinceCode}")
    public String getDistricts(@PathVariable String provinceCode) {
        return addressService.getDistricts(provinceCode);
    }

    @GetMapping("/wards/{districtCode}")
    public String getWards(@PathVariable String districtCode) {
        return addressService.getWards(districtCode);
    }
}
