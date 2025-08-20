package com.fashion_store.dto.order.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {
    @NotNull(message = "INVALID_VARIANT_ID")
    Long variantId;
    @NotNull(message = "INVALID_QUANTITY")
    Integer quantity;
}