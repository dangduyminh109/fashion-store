package com.fashion_store.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "import_receipts")
public class ImportReceipt extends BaseModel {
    String note;
    LocalDateTime importDate;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ImportItem> importItemList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    Supplier supplier;
}
