package com.miniproject.global.jwt.service;

public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
