package com.fashion_store.controller;

import com.fashion_store.dto.request.CustomerCreateRequest;
import com.fashion_store.dto.request.CustomerUpdateRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.CustomerResponse;
import com.fashion_store.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/customer")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    CustomerService customerService;

    @GetMapping()
    public ApiResponse<List<CustomerResponse>> getAll() {
        return ApiResponse.<List<CustomerResponse>>builder()
                .result(customerService.getAll())
                .build();
    }

    @GetMapping("/info/{id}")
    public ApiResponse<CustomerResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<CustomerResponse> create(@ModelAttribute @Valid CustomerCreateRequest request) {
        return ApiResponse.<CustomerResponse>builder()
                .message("Tạo khách hàng thành công")
                .result(customerService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<CustomerResponse> update(@ModelAttribute @Valid CustomerUpdateRequest request, @PathVariable Long id) {
        return ApiResponse.<CustomerResponse>builder()
                .message("Cập nhật khách hàng thành công")
                .result(customerService.update(request, id))
                .build();
    }

    @PatchMapping("/restore/{id}")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        customerService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục khách hàng thành công")
                .build();
    }

    @PatchMapping("/status/{id}")
    public ApiResponse<Void> status(@PathVariable Long id) {
        customerService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<CustomerResponse> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ApiResponse.<CustomerResponse>builder()
                .message("Xóa khách hàng thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    public ApiResponse<CustomerResponse> destroy(@PathVariable Long id) {
        customerService.destroy(id);
        return ApiResponse.<CustomerResponse>builder()
                .message("Khách hàng đã bị xóa vĩnh viễn")
                .build();
    }
}
