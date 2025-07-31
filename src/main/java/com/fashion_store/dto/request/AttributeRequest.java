package com.fashion_store.dto.request;

import com.fashion_store.validator.DisplayTypeConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttributeRequest {
    @NotBlank(message = "INVALID_NAME")
    String name;
    @DisplayTypeConstraint(message = "INVALID_TYPE")
    String displayType;

    List<MultipartFile> image;

    @Size(min = 1, message = "INVALID_ATTRIBUTE_COUNT")
    @NotNull(message = "INVALID_ATTRIBUTE_COUNT")
    List<ListAttributeValueRequest> listAttributeValue;
}
