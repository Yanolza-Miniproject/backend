package com.miniproject.domain.member.request;

public record LoginResponse(
        Long memberId,
        String nickname
) {
}
