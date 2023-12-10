package com.miniproject.domain.refresh.repository;

import com.miniproject.domain.refresh.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByMemberEmail(String email);

    RefreshToken findRefreshTokenByMemberEmail(String memberEmail);

    void deleteRefreshTokenByMemberEmail(String memberEmail);


    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.expiryDate < ?1")
    void deleteAllExpiredSince(LocalDateTime now);

}
