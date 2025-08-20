package com.fashion_store.dto.topic.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicRequest {
    @NotBlank(message = "INVALID_NAME")
    String name;
    @Builder.Default
    Boolean status = true;
}
