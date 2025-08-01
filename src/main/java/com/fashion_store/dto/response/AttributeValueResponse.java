package com.fashion_store.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttributeValueResponse {
    Long id;
    String value;
    String color;
    String image;
    Long attributeId;
    String attributeName;
}
