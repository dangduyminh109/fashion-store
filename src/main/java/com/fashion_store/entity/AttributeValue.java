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
@Table(name = "attribute_values")
public class AttributeValue extends BaseModel {
    @Column(nullable = false)
    String value;
    String color;
    String image;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    Attribute attribute;

    @ManyToMany(mappedBy = "attributeValues")
    private Set<Variant> variants = new HashSet<>();
}
