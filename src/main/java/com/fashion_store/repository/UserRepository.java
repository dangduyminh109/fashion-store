package com.fashion_store.repository;

import com.fashion_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String value);

    boolean existsByEmail(String value);

    Optional<User> findByUsername(String value);

    boolean existsByUsernameAndIdNot(String value, String id);

    boolean existsByEmailAndIdNot(String value, String id);

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN FETCH u.role r " +
            "LEFT JOIN FETCH r.permissions " +
            "WHERE u.username = :username")
    Optional<User> findByUsernameFetchPermissions(@Param("username") String username);

}
