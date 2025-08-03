package com.fashion_store.entity;


import com.fashion_store.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "customers")
public class Customer extends BaseModel {
    @Column(nullable = false)
    String fullName;
    @Column(unique = true)
    String email;
    @Column(unique = true)
    String phone;

    Boolean isGuest;

    String avatar;

    String password;

    @Column(nullable = false)
    AuthProvider authProvider;

    String providerId;

    Boolean status;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Address> addresses;
}
