package com.fashion_store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank(message = "INVALID_NAME")
    String name;
    String description;
    Boolean status;
    Long brandId;
    Long categoryId;
    Double price;
    Integer quantity;
    @NotNull(message = "INVALID_VALUE")
    Boolean hasVariants;
}
