package com.fashion_store.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String name;
    String description;
    Boolean status;
    Double price;
    Integer quantity;
    Boolean hasVariants;
    Boolean isDeleted;
    Long brandId;
    String brandName;
    Long categoryId;
    Long categoryName;
    String slug;
    List<VariantResponse> variantResponses;
}
