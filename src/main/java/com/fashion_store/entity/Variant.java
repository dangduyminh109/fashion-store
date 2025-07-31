package com.fashion_store.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "variants")
public class Variant extends BaseModel {
    String sku;

    @Builder.Default
    Double price = 0.0;
    @Builder.Default
    Integer quantity = 0;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToMany
    @JoinTable(
            name = "variant_attribute_value",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_value_id")
    )
    private Set<AttributeValue> attributeValues = new HashSet<>();
}
