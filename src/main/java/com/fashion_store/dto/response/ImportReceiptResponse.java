package com.fashion_store.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImportReceiptResponse {
    Long id;
    String note;
    LocalDateTime importDate;
    String supplier;
    Boolean isDeleted;
    List<ImportItemResponse> importItemList;
}
