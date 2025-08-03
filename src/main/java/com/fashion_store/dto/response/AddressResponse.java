package com.fashion_store.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponse {
    Long id;
    Long customerId;
    String name;
    String address;
    String phone;
    String city;
    String district;
    String ward;
    Boolean isDefault;
}
