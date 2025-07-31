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
    String name;
    String description;
    Boolean hasVariants;
    Double price;
    Integer quantity;
    String slug;

    @Builder.Default
    Boolean status = true;

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
                "\nprice: ", price,
                "\nquantity: ", quantity,
                "\nhasVariants: ", hasVariants,
                "\nslug: ", slug,
                "\nproductImages: ", productImages.stream().map(ProductImage::getUrl).collect(Collectors.joining(",")),
                "\nstatus: ", status,
                "\ncategory : ", category.getName(),
                "\nbrand: ", brand.getName()
        );
    }
}
