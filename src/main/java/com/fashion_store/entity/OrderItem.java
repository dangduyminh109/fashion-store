package com.fashion_store.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    Order order;

    @ManyToOne(optional = false)
    Variant variant;

    @Column(nullable = false)
    Integer quantity;
    @Column(nullable = false)
    BigDecimal price;
    @Column(nullable = false)
    String productName;
}