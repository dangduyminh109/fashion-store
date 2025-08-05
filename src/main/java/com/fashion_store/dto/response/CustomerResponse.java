package com.fashion_store.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    String id;
    String fullName;
    String email;
    String phone;
    Boolean isGuest;
    String avatar;
    String providerId;
    String authProvider;
    Boolean isDeleted;
    Boolean status;
    List<AddressResponse> addresses;
    String token;
}
