package com.fashion_store.dto.importReceipt.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImportItemResponse {
    Long id;
    Integer quantity = 1;
    BigDecimal importPrice;
    BigDecimal discountAmount;
    String sku;
    String productName;
    String displayName;
}
