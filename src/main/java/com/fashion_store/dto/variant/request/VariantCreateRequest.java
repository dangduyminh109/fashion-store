package com.fashion_store.dto.variant.request;

import com.fashion_store.validator.PriceConstraint;
import com.fashion_store.validator.PromotionTimeConstraint;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@PromotionTimeConstraint(message = "INVALID_PROMOTION_TIME")
@PriceConstraint(message = "INVALID_PRICE")
public class VariantCreateRequest {
    @NotBlank(message = "INVALID_SKU")
    @Size(min = 3, message = "INVALID_SKU")
    String sku;

    BigDecimal originalPrice;

    @Builder.Default
    BigDecimal salePrice = BigDecimal.ZERO;

    BigDecimal promotionalPrice;

    LocalDateTime promotionStartTime;
    LocalDateTime promotionEndTime;

    @Builder.Default
    Boolean status = true;

    @Builder.Default
    Integer inventory = 0;

    List<Long> attributeValue;
}
