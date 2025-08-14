package com.fashion_store.entity;


import com.fashion_store.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    Boolean isGuest;
    String avatar;
    String password;
    String providerId;
    Boolean status;

    @Column(nullable = false)
    String fullName;

    @Column(unique = true)
    String email;

    String phone;

    @Column(nullable = false)
    AuthProvider authProvider;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "customer", cascade = CascadeType.REMOVE)
    Cart cart;

    @OneToMany(mappedBy = "customer")
    List<Order> orders = new ArrayList<>();
}
