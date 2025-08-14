package com.fashion_store.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryTreeResponse {
    Long id;
    String name;
    String slug;
    List<CategoryTreeResponse> children;
}
