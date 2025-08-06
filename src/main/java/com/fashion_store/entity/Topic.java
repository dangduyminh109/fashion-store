package com.fashion_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "topics")
public class Topic extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String name;
    @Column(nullable = false, unique = true)
    String slug;
    Boolean status;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    List<Post> posts = new ArrayList<>();

    @Override
    public String toString() {
        return String.format(
                super.toString(),
                "\nname: ", name,
                "\nslug: ", slug,
                "\nstatus: ", status
        );
    }
}
