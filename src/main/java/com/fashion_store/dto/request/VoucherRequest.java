package com.fashion_store.dto.request;

import com.fashion_store.validator.VoucherDiscountConstraint;
import com.fashion_store.validator.VoucherTimeConstraint;
import com.fashion_store.validator.VoucherTypeConstraint;
import jakarta.validation.constraints.DecimalMin;
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
public class VoucherRequest {
    @NotBlank(message = "INVALID_NAME")
    String name;
    @NotBlank(message = "INVALID_CODE")
    String code;
    @Builder.Default
    Boolean status = true;

    @Builder.Default
    Integer quantity = 1;

    @Builder.Default
    String description = "";

    @Builder.Default
    LocalDateTime startDate = LocalDateTime.now();

    LocalDateTime endDate;

    @VoucherTypeConstraint(message = "INVALID_VOUCHER_TYPE")
    @Builder.Default
    String discountType = "AMOUNT";

    BigDecimal discountValue;

    BigDecimal maxDiscountValue;

    @Builder.Default
    @DecimalMin(value = "0", inclusive = true, message = "INVALID_ORDER_VALUE")
    BigDecimal minOrderValue = BigDecimal.ZERO;
}
