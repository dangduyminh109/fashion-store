package com.fashion_store.dto.component;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttributeValueItem {
    @NotBlank(message = "INVALID_VALUE")
    String value;
    Long id;
    String color;
    Boolean imageDelete;
}
