package com.fashion_store.dto.cart.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequest {
    @Builder.Default
    @Min(value = 1, message = "INVALID_QUANTITY")
    Integer quantity = 1;
    @NotNull(message = "INVALID_PRODUCT_ID")
    Long variantId;
}
