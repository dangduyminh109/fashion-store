package com.fashion_store.repository;

import com.fashion_store.entity.Permission;
import com.fashion_store.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
