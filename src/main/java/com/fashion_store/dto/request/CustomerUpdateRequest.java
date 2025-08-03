package com.fashion_store.dto.request;

import com.fashion_store.validator.AuthProviderConstraint;
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
public class CustomerUpdateRequest {
    @NotBlank(message = "INVALID_NAME")
    String fullName;
    String email;
    String phone;
    Boolean isGuest;
    String password;
    String providerId;
    Boolean status;

    @AuthProviderConstraint(message = "INVALID_AUTH_PROVIDER")
    String authProvider;

    @NotNull(message = "INVALID_FILE")
    MultipartFile avatar;

    @Builder.Default
    Boolean avatarDelete = false;
}
