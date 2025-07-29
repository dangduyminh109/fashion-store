package com.fashion_store.repository;

import com.fashion_store.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByName(String name);

    List<Brand> findBySlugStartingWith(String slug);

    boolean existsByNameAndIdNot(String name, Long id);

}
