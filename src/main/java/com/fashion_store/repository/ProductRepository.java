package com.fashion_store.repository;

import com.fashion_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    List<Product> findBySlugStartingWith(String slug);

    boolean existsByNameAndIdNot(String name, Long id);
}
