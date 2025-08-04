package com.fashion_store.entity;

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
@Table(name = "suppliers")
public class Supplier extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    String email;
    String phone;
    String address;
    Boolean status;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    List<ImportReceipt> importReceipts;

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