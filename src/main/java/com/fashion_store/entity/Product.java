package com.fashion_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseModel {
    @Column(nullable = false)
    String name;

    String description;

    @Column(nullable = false, unique = true)
    String slug;

    Boolean status;

    Boolean isFeatured;

    Long position;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Variant> variants;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;

    @Override
    public String toString() {
        return String.format(
                super.toString(),
                "\nname: ", name,
                "\nslug: ", slug,
                "\nproductImages: ", productImages.stream().map(ProductImage::getUrl).collect(Collectors.joining(",")),
                "\nstatus: ", status,
                "\ncategory : ", category.getName(),
                "\nbrand: ", brand.getName()
        );
    }
}
