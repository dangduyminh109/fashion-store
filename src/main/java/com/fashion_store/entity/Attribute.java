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
    String name;

    @Builder.Default
    AttributeDisplayType displayType = AttributeDisplayType.TEXT;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    List<AttributeValue> attributeValues;
}
