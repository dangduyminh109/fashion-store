package com.fashion_store.entity;

import com.fashion_store.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vouchers")
public class Voucher extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false, unique = true)
    String code;
    Boolean status;
    Integer used;
    @Column(nullable = false)
    Integer quantity;
    String description;
    LocalDateTime endDate;
    LocalDateTime startDate;
    @Column(nullable = false)
    BigDecimal discountValue;
    @Column(nullable = false)
    DiscountType discountType;
    BigDecimal maxDiscountValue;
    BigDecimal minOrderValue;
}