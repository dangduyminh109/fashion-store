package com.fashion_store.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImportReceiptUpdateRequest {
    @Builder.Default
    String note = "";

    @Builder.Default
    LocalDateTime importDate = LocalDateTime.now();

    Long supplierId;
}
