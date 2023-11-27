package com.miniproject.domain.refresh.repository;

import com.miniproject.domain.refresh.entity.RefreshToken;
import com.miniproject.global.jwt.repository.JwtEntity;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void save(JwtEntity entity);


    RefreshToken findByMemberId(Long id);
}
