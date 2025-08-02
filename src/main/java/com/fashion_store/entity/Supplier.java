package com.fashion_store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Supplier extends BaseModel {
    @Column(nullable = false)
    String name;
    String email;
    String phone;
    String address;
    Boolean status;

    @Override
    public String toString() {
        return String.format(
                super.toString(),
                "\nname: ", name,
                "\nemail: ", email,
                "\nphone: ", phone,
                "\naddress: ", address,
                "\nstatus: ", status
        );
    }
}