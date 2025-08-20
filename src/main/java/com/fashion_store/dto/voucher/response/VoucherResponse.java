package com.fashion_store.dto.voucher.response;

import com.fashion_store.enums.DiscountType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherResponse {
    Long id;
    String name;
    String code;
    Integer used;
    Boolean status;
    Boolean isDeleted;
    Integer quantity;
    String description;
    LocalDateTime endDate;
    LocalDateTime startDate;
    BigDecimal discountValue;
    BigDecimal minOrderValue;
    DiscountType discountType;
    BigDecimal maxDiscountValue;
}
