package com.fashion_store.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariantRequest {
    @Size(min = 3, message = "SKU_INVALID")
    String sku;
    Double price;
    Integer quantity;
}
