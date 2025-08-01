package com.fashion_store.repository;

import com.fashion_store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    List<Category> findBySlugStartingWith(String slug);

    boolean existsByNameAndIdNot(String name, Long id);
}
