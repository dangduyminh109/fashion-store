package com.fashion_store.mapper;

import com.fashion_store.dto.role.request.RoleRequest;
import com.fashion_store.dto.role.resonse.RoleResponse;
import com.fashion_store.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {PermissionMapper.class})
public interface RoleMapper {
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);

    void updateRole(@MappingTarget Role role, RoleRequest roleRequest);
}
