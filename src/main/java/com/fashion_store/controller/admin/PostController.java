package com.fashion_store.controller.admin;

import com.fashion_store.dto.post.request.PostRequest;
import com.fashion_store.dto.common.response.ApiResponse;
import com.fashion_store.dto.post.response.PostResponse;
import com.fashion_store.service.PostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/post")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;

    @GetMapping
    @PreAuthorize("hasAuthority('POST_VIEW')")
    public ApiResponse<List<PostResponse>> getAll(
            @RequestParam(value = "deleted", required = false) boolean deleted
    ) {
        return ApiResponse.<List<PostResponse>>builder()
                .result(postService.getAll(deleted))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('POST_VIEW')")
    public ApiResponse<PostResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<PostResponse>builder()
                .result(postService.getInfo(id))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('POST_CREATE')")
    public ApiResponse<PostResponse> create(@ModelAttribute @Valid PostRequest request) {
        return ApiResponse.<PostResponse>builder()
                .message("Tạo bài đăng thành công")
                .result(postService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('POST_UPDATE')")
    public ApiResponse<PostResponse> update(@ModelAttribute @Valid PostRequest request, @PathVariable Long id) {
        return ApiResponse.<PostResponse>builder()
                .message("Cập nhật bài đăng thành công")
                .result(postService.update(request, id))
                .build();
    }

    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasAuthority('POST_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        postService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục bài đăng thành công")
                .build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('POST_UPDATE')")
    public ApiResponse<Void> status(@PathVariable Long id) {
        postService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('POST_DELETE')")
    public ApiResponse<PostResponse> delete(@PathVariable Long id) {
        postService.delete(id);
        return ApiResponse.<PostResponse>builder()
                .message("Xóa bài đăng thành công")
                .build();
    }

    @DeleteMapping("/{id}/destroy")
    @PreAuthorize("hasAuthority('POST_DELETE')")
    public ApiResponse<PostResponse> destroy(@PathVariable Long id) {
        postService.destroy(id);
        return ApiResponse.<PostResponse>builder()
                .message("Bài đăng đã bị xóa vĩnh viễn")
                .build();
    }
}
