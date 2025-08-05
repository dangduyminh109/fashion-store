package com.fashion_store.controller;

import com.fashion_store.dto.request.UserCreateRequest;
import com.fashion_store.dto.request.UserUpdateRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.UserResponse;
import com.fashion_store.service.UserService;
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
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping()
    @PreAuthorize("hasAuthority('USER_VIEW')")
    public ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('USER_VIEW')")
    public ApiResponse<UserResponse> getInfo(@PathVariable String id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ApiResponse<UserResponse> create(@ModelAttribute @Valid UserCreateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("Tạo tài khoản thành công")
                .result(userService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ApiResponse<UserResponse> update(@ModelAttribute @Valid UserUpdateRequest request, @PathVariable String id) {
        return ApiResponse.<UserResponse>builder()
                .message("Cập nhật tài khoản thành công")
                .result(userService.update(request, id))
                .build();
    }

    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable String id) {
        userService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục tài khoản thành công")
                .build();
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ApiResponse<Void> status(@PathVariable String id) {
        userService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ApiResponse<UserResponse> delete(@PathVariable String id) {
        userService.delete(id);
        return ApiResponse.<UserResponse>builder()
                .message("Xóa tài khoản thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ApiResponse<UserResponse> destroy(@PathVariable String id) {
        userService.destroy(id);
        return ApiResponse.<UserResponse>builder()
                .message("Tài khoản đã bị xóa vĩnh viễn")
                .build();
    }
}
