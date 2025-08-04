package com.fashion_store.service;

import com.fashion_store.dto.request.RoleRequest;
import com.fashion_store.dto.request.UpdateRolePermissionsRequest;
import com.fashion_store.dto.response.RoleResponse;
import com.fashion_store.entity.Permission;
import com.fashion_store.entity.Role;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import com.fashion_store.mapper.RoleMapper;
import com.fashion_store.repository.PermissionRepository;
import com.fashion_store.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request) {
        if (roleRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.EXISTED);
        Role role = roleMapper.toRole(request);

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());
    }

    public RoleResponse getInfo(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        return roleMapper.toRoleResponse(role);
    }

    public RoleResponse update(RoleRequest request, Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        if (role.getName().equals("ADMIN"))
            throw new AppException(ErrorCode.CANNOT_BE_UPDATE);
        if (roleRepository.existsByNameAndIdNot(request.getName(), id))
            throw new AppException(ErrorCode.EXISTED);
        roleMapper.updateRole(role, request);
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public void updatePermissionsForRoles(UpdateRolePermissionsRequest updates) {
        if (!updates.getUpdates().isEmpty()) {
            updates.getUpdates().forEach(item -> {
                Role role = roleRepository.findById(item.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
                if (!role.getName().equals("ADMIN")) {
                    role.getPermissions().clear();
                    Set<Permission> newPermission = new HashSet<>(permissionRepository.findAllById(item.getPermissionIds()));
                    role.getPermissions().addAll(newPermission);
                    roleRepository.save(role);
                }
            });
        }
    }

    public void destroy(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        if (role.getName().equals("ADMIN"))
            throw new AppException(ErrorCode.CANNOT_BE_DELETE);
        roleRepository.deleteById(id);
    }

}
