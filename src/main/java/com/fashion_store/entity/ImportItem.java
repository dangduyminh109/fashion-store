package com.fashion_store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    Integer quantity;
    BigDecimal importPrice;
    BigDecimal discountAmount;

    @ManyToOne
    Variant variant;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    ImportReceipt receipt;
}
