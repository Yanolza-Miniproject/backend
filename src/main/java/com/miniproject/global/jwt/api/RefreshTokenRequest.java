package com.miniproject.global.jwt.api;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank String accessToken,
        @NotBlank String refreshToken
) {
}
