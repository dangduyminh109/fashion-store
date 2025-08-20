package com.fashion_store.dto.role.component;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRolePermissions {
    @NotNull(message = "INVALID_ROLE_ID")
    Long id;
    @NotNull(message = "INVALID_LIST_PERMISSION_ID")
    List<Long> permissionIds;
}