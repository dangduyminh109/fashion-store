package com.fashion_store.dto.request;

import com.fashion_store.validator.VoucherDiscountConstraint;
import com.fashion_store.validator.VoucherTimeConstraint;
import com.fashion_store.validator.VoucherTypeConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@VoucherTimeConstraint(message = "INVALID_VOUCHER_TIME")
@VoucherDiscountConstraint(message = "INVALID_VOUCHER_DISCOUNT")
public class VoucherUpdateRequest {
    @NotBlank(message = "INVALID_NAME")
    String name;
    @NotBlank(message = "INVALID_CODE")
    String code;
    Boolean status;

    Integer quantity;

    String description;

    LocalDateTime startDate;

    LocalDateTime endDate;

    @VoucherTypeConstraint(message = "INVALID_VOUCHER_TYPE")
    String discountType;

    BigDecimal discountValue;
    BigDecimal maxDiscountValue;
    BigDecimal minOrderValue;
}
