package com.fashion_store.controller;

import com.fashion_store.dto.request.RoleRequest;
import com.fashion_store.dto.request.UpdateRolePermissionsRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.PermissionResponse;
import com.fashion_store.dto.response.RoleResponse;
import com.fashion_store.service.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_VIEW')")
    public ApiResponse<List<RoleResponse>> getAll(
            @RequestParam(value = "deleted", required = false) boolean deleted
    ) {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll(deleted))
                .build();
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ROLE_VIEW')")
    public ApiResponse<RoleResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ApiResponse<RoleResponse> create(@RequestBody @Valid RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .message("Tạo vai trò thành công")
                .result(roleService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ApiResponse<RoleResponse> update(@RequestBody @Valid RoleRequest request, @PathVariable Long id) {
        return ApiResponse.<RoleResponse>builder()
                .message("Cập nhật vai trò thành công")
                .result(roleService.update(request, id))
                .build();
    }

    @GetMapping("/permission")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    public ApiResponse<List<PermissionResponse>> getPermission() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(roleService.getPermission())
                .build();
    }

    @PutMapping("/update-permissions")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    public ApiResponse<Void> updatePermissionsForRoles(@RequestBody @Valid UpdateRolePermissionsRequest request) {
        roleService.updatePermissionsForRoles(request);
        return ApiResponse.<Void>builder()
                .message("Cập nhật quyền cho vai trò thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ApiResponse<RoleResponse> destroy(@PathVariable Long id) {
        roleService.destroy(id);
        return ApiResponse.<RoleResponse>builder()
                .message("Vai trò đã bị xóa vĩnh viễn")
                .build();
    }
}
