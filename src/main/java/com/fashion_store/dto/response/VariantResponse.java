package com.fashion_store.dto.response;

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
public class VariantResponse {
    Long id;
    String sku;
    Boolean status;
    Integer inventory;
    BigDecimal salePrice;
    BigDecimal originalPrice;
    BigDecimal promotionalPrice;
    LocalDateTime promotionEndTime;
    LocalDateTime promotionStartTime;
    List<AttributeValueResponse> attributeValues;
}
