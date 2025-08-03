package com.fashion_store.dto.response;

import com.fashion_store.validator.ImportItemPriceConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ImportItemPriceConstraint(message = "INVALID_IMPORT_PRICE")
public class ImportItemResponse {
    Long id;
    Integer quantity = 1;
    BigDecimal importPrice;
    BigDecimal discountAmount;
    String sku;
    String productName;
}
