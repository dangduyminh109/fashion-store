package com.fashion_store.mapper;

import com.fashion_store.dto.request.UserCreateRequest;
import com.fashion_store.dto.request.UserUpdateRequest;
import com.fashion_store.dto.response.UserResponse;
import com.fashion_store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toUser(UserCreateRequest userCreateRequest);

    @Mapping(target = "roleName", expression = "java(user.getRole() != null ? user.getRole().getName() : null)")
    @Mapping(target = "roleId", expression = "java(user.getRole() != null ? user.getRole().getId() : null)")
    UserResponse toUserResponse(User user);

    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
