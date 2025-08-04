package com.fashion_store.repository;

import com.fashion_store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByFullName(String value);

    boolean existsByEmail(String value);

    boolean existsByPhone(String value);

    boolean existsByFullNameAndIdNot(String value, String id);

    boolean existsByEmailAndIdNot(String value, String id);

    boolean existsByPhoneAndIdNot(String value, String id);
}
