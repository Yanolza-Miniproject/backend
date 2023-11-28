package com.miniproject.global.security.jwt;

public record JwtPair(
        String accessToken,
        String refreshToken
) {
}
