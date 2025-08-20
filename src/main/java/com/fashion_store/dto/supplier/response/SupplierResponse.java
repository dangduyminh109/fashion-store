package com.fashion_store.dto.supplier.response;

import com.fashion_store.validator.PhoneConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplierResponse {
    Long id;
    @NotBlank(message = "INVALID_NAME")
    String name;
    @Email(message = "INVALID_EMAIL")
    String email;
    @PhoneConstraint
    String phone;
    String address;
    Boolean status;
    Boolean isDeleted;
}
