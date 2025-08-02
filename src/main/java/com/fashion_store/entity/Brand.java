package com.fashion_store.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Brand extends BaseModel {
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
