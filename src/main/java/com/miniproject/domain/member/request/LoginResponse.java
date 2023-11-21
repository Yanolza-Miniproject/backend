package com.miniproject.domain.member.request;

public record LoginResponse(
        Long id,
        String accessToken,
        String refreshToken
) {

}
