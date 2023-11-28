package com.miniproject.global.jwt;

import com.miniproject.global.jwt.exception.BadTokenException;
import com.miniproject.global.jwt.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenProvider {

    private static final String USER_KEY = "user-key";

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    @Value("${service.jwt.refresh-expiration}")
    private Long refreshExpiration;

    private final SecretKey secretKey;

    public JwtTokenProvider(@Value("${service.jwt.secret-key}") String rawSecretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(rawSecretKey));
    }

    public String createAccessTokenOrRefreshToken(JwtPayload jwtPayload, long expiration){

        return Jwts.builder()
                .claim(USER_KEY, Objects.requireNonNull(jwtPayload.email()))
                .issuer(issuer)
                .issuedAt(Objects.requireNonNull(jwtPayload.issuedAt()))
                .expiration(new Date(jwtPayload.issuedAt().getTime() + expiration))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public JwtToken generateToken(JwtPayload jwtPayload){
        String accessToken = createAccessTokenOrRefreshToken(jwtPayload, accessExpiration);
        String refreshToken = createAccessTokenOrRefreshToken(jwtPayload, refreshExpiration);

        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String createAccessToken(JwtPayload jwtPayload) {
        return createAccessTokenOrRefreshToken(jwtPayload, accessExpiration);
    }

    public String createRefreshToken(JwtPayload jwtPayload) {
        return createAccessTokenOrRefreshToken(jwtPayload, refreshExpiration);
    }

    public JwtPayload verifyToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(jwtToken);
            Claims payload = claimsJws.getPayload();

            return new JwtPayload(payload.get(USER_KEY, String.class), payload.getIssuedAt());
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(e.getMessage());
        } catch (JwtException e) {
            throw new BadTokenException(e.getMessage());
        }
    }


    }



