package com.miniproject.domain.Member.request;

public record LoginResponse(
        Long id,
        String accessToken,
        String refreshToken
) {

}
