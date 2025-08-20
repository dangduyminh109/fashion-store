package com.fashion_store.dto.importReceipt.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImportReceiptRequest {
    @Builder.Default
    String note = "";

    @Builder.Default
    LocalDateTime importDate = LocalDateTime.now();

    Long supplierId;

    @NotNull(message = "INVALID_LIST_IMPORT_ITEM")
    @Size(min = 1, message = "INVALID_LIST_IMPORT_ITEM")
    @Valid
    List<ImportItemRequest> importItemList;
}
