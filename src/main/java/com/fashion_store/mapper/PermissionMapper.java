package com.fashion_store.mapper;

import com.fashion_store.dto.response.PermissionResponse;
import com.fashion_store.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(Permission permission);
}
