package com.fashion_store.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String sku;
    Integer inventory;

    BigDecimal originalPrice;
    BigDecimal promotionalPrice;

    @Column(nullable = false)
    BigDecimal salePrice;

    Boolean status;

    LocalDateTime promotionStartTime;
    LocalDateTime promotionEndTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<CartItem> cartItems = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "variant_attribute_value",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_value_id")
    )
    Set<AttributeValue> attributeValues = new HashSet<>();

    @Override
    public String toString() {
        return String.format(
                super.toString(),
                "\nsku: ", sku,
                "\noriginalPrice: ", originalPrice,
                "\nsalePrice: ", salePrice,
                "\npromotionalPrice: ", promotionalPrice,
                "\ninventory: ", inventory,
                "\npromotionStartTime: ", promotionStartTime,
                "\npromotionEndTime : ", promotionEndTime
        );
    }
}
