package com.fashion_store.repository;

import com.fashion_store.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    Role findByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
