package com.fashion_store.repository;

import com.fashion_store.entity.Variant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
    boolean existsBySku(String sku);

    Optional<Variant> findBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, Long id);


    @EntityGraph(attributePaths = {"attributeValues", "attributeValues.attribute"})
    Optional<Variant> findById(Long id);

    @EntityGraph(attributePaths = {"attributeValues", "attributeValues.attribute"})
    List<Variant> findByProductId(Long productId);
}
