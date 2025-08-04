package com.fashion_store.dto.request;

import com.fashion_store.dto.component.UpdateRolePermissions;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRolePermissionsRequest {
    @Valid
    List<UpdateRolePermissions> updates;
}
