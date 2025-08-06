package com.fashion_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String firstName;
    String lastName;
    Boolean status;
    String avatar;
    String password;
    String phone;

    @Column(nullable = false, unique = true)
    String username;

    @Column(unique = true)
    String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
}
