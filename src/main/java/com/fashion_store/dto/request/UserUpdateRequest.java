package com.fashion_store.dto.request;

import com.fashion_store.validator.AuthProviderConstraint;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotBlank(message = "INVALID")
    String username;
    String password;
    String firstName;
    String lastName;
    String phone;
    Boolean status;
    String email;

    @NotNull(message = "INVALID_ROLE_ID")
    Long roleId;

    @NotNull(message = "INVALID_FILE")
    MultipartFile avatar;

    @Builder.Default
    Boolean avatarDelete = false;
}
