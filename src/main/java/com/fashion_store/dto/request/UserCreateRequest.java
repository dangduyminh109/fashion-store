package com.fashion_store.dto.request;

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
public class UserCreateRequest {
    @NotBlank(message = "INVALID")
    String username;
    String password;
    String firstName;
    String lastName;
    String phone;

    @Column(unique = true)
    String email;

    @NotNull(message = "INVALID_ROLE_ID")
    Long roleId;

    @NotNull(message = "INVALID_FILE")
    MultipartFile avatar;

    @Builder.Default
    Boolean status = true;
}



