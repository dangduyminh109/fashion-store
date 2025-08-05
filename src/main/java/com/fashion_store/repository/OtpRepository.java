package com.fashion_store.repository;

import com.fashion_store.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmailAndCode(String email, String otpCode);

    Optional<Otp> findFirstByEmailAndExpiryTimeAfter(String email, LocalDateTime localDateTime);
}
