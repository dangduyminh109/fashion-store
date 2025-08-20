package com.fashion_store.dto.user.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String firstName;
    String lastName;
    Boolean status;
    String avatar;
    String phone;
    String username;
    String email;
    Boolean isDeleted;
    Long roleId;
    String roleName;
    String token;
}
