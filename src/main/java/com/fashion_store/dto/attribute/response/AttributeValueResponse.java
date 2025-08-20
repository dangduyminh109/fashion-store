package com.fashion_store.dto.attribute.response;

import com.fashion_store.enums.AttributeDisplayType;
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
    AttributeDisplayType displayType;
}
