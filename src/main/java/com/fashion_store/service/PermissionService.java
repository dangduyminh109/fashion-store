package com.fashion_store.service;

import com.fashion_store.dto.response.PermissionResponse;
import com.fashion_store.entity.Permission;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.mapper.PermissionMapper;
import com.fashion_store.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toPermissionResponse)
                .collect(Collectors.toList());
    }
}
