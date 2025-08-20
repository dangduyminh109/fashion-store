package com.fashion_store.dto.category.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Long id;
    String name;
    String slug;
    Boolean status;
    String image;
    Long parentId;
    String parentName;
    Boolean isDeleted;
}
