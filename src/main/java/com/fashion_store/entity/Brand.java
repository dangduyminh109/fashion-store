package com.fashion_store.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "brands")
public class Brand extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String name;
    String image;
    @Column(nullable = false, unique = true)
    String slug;
    Boolean status;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Product> products;

    @Override
    public String toString() {
        return String.format(
                super.toString(),
                "\nname: ", name,
                "\nslug: ", slug,
                "\nimage: ", image,
                "\nstatus: ", status
        );
    }
}
