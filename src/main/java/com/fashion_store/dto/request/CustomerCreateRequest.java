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
public class CustomerCreateRequest {
    @NotBlank(message = "INVALID")
    String fullName;
    String email;
    String phone;
    Boolean isGuest;
    String password;
    String providerId;

    @AuthProviderConstraint(message = "INVALID_AUTH_PROVIDER")
    @Builder.Default
    String authProvider = "GUEST";

    @NotNull(message = "INVALID_FILE")
    MultipartFile avatar;

    @Builder.Default
    Boolean status = true;
}
