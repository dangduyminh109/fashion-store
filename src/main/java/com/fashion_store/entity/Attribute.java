package com.fashion_store.entity;

import com.fashion_store.enums.AttributeDisplayType;
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
@Table(name = "attributes")
public class Attribute extends BaseModel {
    @Column(nullable = false)
    String name;

    AttributeDisplayType displayType;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    List<AttributeValue> attributeValues;
}
