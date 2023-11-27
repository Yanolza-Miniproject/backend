package com.miniproject.global.jwt;

import javax.crypto.SecretKey;

import com.miniproject.global.jwt.exception.BadTokenException;
import com.miniproject.global.jwt.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class JwtProvider {

    private static final String USER_KEY = "user-key";

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    @Value("${service.jwt.refresh-expiration}")
    private Long refreshExpiration;

    private final SecretKey secretKey;

    public JwtProvider(@Value("${service.jwt.secret-key}") String rawSecretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(rawSecretKey));
    }

    public String createAccessToken(JwtPayload jwtPayload) {
        return createToken(jwtPayload, accessExpiration);
    }

    public String createRefreshToken(JwtPayload jwtPayload) {
        return createToken(jwtPayload, refreshExpiration);
    }

    public String createToken(JwtPayload jwtPayload, long expiration) {
        return Jwts.builder()
                .claim(USER_KEY, Objects.requireNonNull(jwtPayload.email()))
                .issuer(issuer)
                .issuedAt(Objects.requireNonNull(jwtPayload.issuedAt()))
                .expiration(new Date(jwtPayload.issuedAt().getTime() + expiration))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public JwtPayload verifyToken(String jwtToken) {
        log.info("JwtProvider. verifyToken 시작 ");
        try {
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(jwtToken);
            Claims payload = claimsJws.getPayload();

            String userKey = payload.get(USER_KEY, String.class);
            log.info("userKey : {}", userKey);
            if(userKey == null || userKey.isEmpty()) {
                throw new BadTokenException("Invalid token: missing user key");
            }

            log.info("검증 성공, 발급날짜 : {}", payload.getIssuedAt());
            return new JwtPayload(payload.get(USER_KEY, String.class), payload.getIssuedAt());
        } catch (ExpiredJwtException e) {
            log.info("catch ExpiredJwtException");
            throw new TokenExpiredException(e.getMessage());
        } catch (JwtException e) {
            log.info("catch JwtException");
            throw new BadTokenException(e.getMessage());
        }
    }
}

