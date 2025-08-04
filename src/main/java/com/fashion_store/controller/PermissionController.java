package com.fashion_store.controller;

import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.PermissionResponse;
import com.fashion_store.service.PermissionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/permission")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @GetMapping()
    public ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @GetMapping("/info/{id}")
    public ApiResponse<PermissionResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.getInfo(id))
                .build();
    }
}
