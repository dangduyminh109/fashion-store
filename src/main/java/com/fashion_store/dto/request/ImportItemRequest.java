package com.fashion_store.dto.request;

import com.fashion_store.validator.ImportItemPriceConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ImportItemPriceConstraint(message = "INVALID_IMPORT_PRICE")
public class ImportItemRequest {
    @NotNull(message = "INVALID_QUANTITY")
    @Builder.Default
    Integer quantity = 1;

    @NotNull(message = "INVALID_IMPORT_PRICE")
    BigDecimal importPrice;

    BigDecimal discountAmount;

    @NotNull(message = "INVALID_VARIANT_ID")
    Long variantId;
}
