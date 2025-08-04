package com.fashion_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "posts")
public class Post extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String content;
    String image;
    @Column(nullable = false, unique = true)
    String slug;
    Boolean status;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    Topic topic;

    @Override
    public String toString() {
        return String.format(
                super.toString(),
                "\ntitle: ", title,
                "\ncontent: ", content,
                "\nimage: ", image
        );
    }
}