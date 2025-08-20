package com.fashion_store.dto.product.response;

import com.fashion_store.dto.category.response.CategoryResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFromCategoryResponse {
    CategoryResponse category;
    List<ProductResponse> products;
}
