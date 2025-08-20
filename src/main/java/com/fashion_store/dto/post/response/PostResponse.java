package com.fashion_store.dto.post.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    Long id;
    String title;
    String content;
    Boolean status;
    Boolean isDeleted;
    String slug;
    Long topicId;
    String topicName;
    String image;
}
