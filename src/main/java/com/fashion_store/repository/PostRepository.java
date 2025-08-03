package com.fashion_store.repository;

import com.fashion_store.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTitle(String title);

    List<Post> findBySlugStartingWith(String slug);

    boolean existsByTitleAndIdNot(String title, Long id);
}
