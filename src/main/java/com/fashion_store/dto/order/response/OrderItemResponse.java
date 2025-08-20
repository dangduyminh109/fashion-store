package com.fashion_store.dto.order.response;

import com.fashion_store.dto.variant.response.VariantResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    Long id;
    Integer quantity;
    VariantResponse variant;
    BigDecimal price;
    String productName;
}
