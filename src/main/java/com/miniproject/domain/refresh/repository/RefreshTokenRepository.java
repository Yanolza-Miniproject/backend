package com.miniproject.domain.refresh.repository;

import com.miniproject.domain.refresh.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByMemberEmail(String email);

    RefreshToken findRefreshTokenByMemberEmail(String memberEmail);


}
