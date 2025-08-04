package com.fashion_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "import_items")
public class ImportItem extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer quantity;
    BigDecimal importPrice;
    BigDecimal discountAmount;

    @ManyToOne
    Variant variant;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    ImportReceipt receipt;
}
