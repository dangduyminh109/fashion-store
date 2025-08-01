package com.fashion_store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttributeValueUpdateRequest {
    @NotBlank(message = "INVALID_VALUE")
    String value;
    String color;
    @NotNull(message = "INVALID_FILE")
    MultipartFile image;
    Boolean imageDelete;
}
