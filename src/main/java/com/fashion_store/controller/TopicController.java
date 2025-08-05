package com.fashion_store.controller;

import com.fashion_store.dto.request.TopicRequest;
import com.fashion_store.dto.response.ApiResponse;
import com.fashion_store.dto.response.TopicResponse;
import com.fashion_store.service.TopicService;
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
@RequestMapping("/topic")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopicController {
    TopicService topicService;

    @GetMapping()
    @PreAuthorize("hasAuthority('TOPIC_VIEW')")
    public ApiResponse<List<TopicResponse>> getAll() {
        return ApiResponse.<List<TopicResponse>>builder()
                .result(topicService.getAll())
                .build();
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('TOPIC_VIEW')")
    public ApiResponse<TopicResponse> getInfo(@PathVariable Long id) {
        return ApiResponse.<TopicResponse>builder()
                .result(topicService.getInfo(id))
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('TOPIC_CREATE')")
    public ApiResponse<TopicResponse> create(@RequestBody @Valid TopicRequest request) {
        return ApiResponse.<TopicResponse>builder()
                .message("Tạo chủ đề thành công")
                .result(topicService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('TOPIC_UPDATE')")
    public ApiResponse<TopicResponse> update(@RequestBody @Valid TopicRequest request, @PathVariable Long id) {
        return ApiResponse.<TopicResponse>builder()
                .message("Cập nhật chủ đề thành công")
                .result(topicService.update(request, id))
                .build();
    }

    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('TOPIC_UPDATE')")
    public ApiResponse<Void> restore(@PathVariable Long id) {
        topicService.restore(id);
        return ApiResponse.<Void>builder()
                .message("Khôi phục chủ đề thành công")
                .build();
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAuthority('TOPIC_UPDATE')")
    public ApiResponse<Void> status(@PathVariable Long id) {
        topicService.status(id);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái thành công")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('TOPIC_DELETE')")
    public ApiResponse<TopicResponse> delete(@PathVariable Long id) {
        topicService.delete(id);
        return ApiResponse.<TopicResponse>builder()
                .message("Xóa chủ đề thành công")
                .build();
    }

    @DeleteMapping("/destroy/{id}")
    @PreAuthorize("hasAuthority('TOPIC_DELETE')")
    public ApiResponse<TopicResponse> destroy(@PathVariable Long id) {
        topicService.destroy(id);
        return ApiResponse.<TopicResponse>builder()
                .message("Chủ đề đã bị xóa vĩnh viễn")
                .build();
    }
}
