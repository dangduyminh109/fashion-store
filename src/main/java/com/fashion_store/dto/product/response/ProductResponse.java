package com.fashion_store.dto.product.response;

import com.fashion_store.dto.variant.response.VariantResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    String description;
    Boolean status;
    Boolean isFeatured;
    Boolean isDeleted;
    Long brandId;
    String brandName;
    Long categoryId;
    String categoryName;
    String slug;
    List<VariantResponse> variants;
    List<String> productImages;
}
