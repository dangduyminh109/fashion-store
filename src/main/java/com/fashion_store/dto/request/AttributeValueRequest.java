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
public class AttributeValueRequest {
    @NotBlank(message = "INVALID_VALUE")
    String value;
    @NotNull(message = "INVALID_VALUE")
    Long AttributeId;
    @Builder.Default
    String color = "#ccc";
    @NotNull(message = "INVALID_FILE")
    MultipartFile image;
}
