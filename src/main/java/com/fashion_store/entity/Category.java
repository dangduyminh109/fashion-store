package com.fashion_store.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "categories")
public class Category extends BaseModel {
    @Column(unique = true)
    String name;
    String image;
    @Column(unique = true)
    String slug;
    Boolean status;


    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Category> children;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<Product> products;

    @Override
    public String toString() {
        return String.format(
                super.toString(),
                "\nname: ", name,
                "\nslug: ", slug,
                "\nimage: ", image,
                "\nstatus: ", status,
                "\nparentID: ", parent.getId(),
                "\nparentName: ", parent.getName()
        );
    }
}
