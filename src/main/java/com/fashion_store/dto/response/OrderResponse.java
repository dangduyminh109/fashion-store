package com.fashion_store.dto.response;


import com.fashion_store.enums.DiscountType;
import com.fashion_store.enums.OrderStatus;
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
public class OrderResponse {
    String id;
    String phone;
    String address;
    String city;
    String district;
    String ward;
    String note;
    String paymentMethod;
    Boolean isDeleted;
    boolean isPaid;
    LocalDateTime paidAt;
    OrderStatus orderStatus;
    BigDecimal totalAmount;
    BigDecimal totalDiscount;
    BigDecimal finalAmount;
    String customerId;
    String customerName;
    Long voucherId;
    String voucherName;
    DiscountType discountType;
    List<OrderItemResponse> orderItems;
}
