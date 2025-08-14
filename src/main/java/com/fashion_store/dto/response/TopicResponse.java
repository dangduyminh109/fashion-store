package com.fashion_store.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicResponse {
    Long id;
    String name;
    String slug;
    Boolean status;
    Boolean isDeleted;
}
