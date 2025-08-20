package com.fashion_store.dto.cart.response;

import com.fashion_store.dto.variant.response.VariantClientResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    VariantClientResponse variant;
    Integer quantity;
}
