package com.miniproject.domain.member.request;

public record LoginRequest(
        String email,
        String password
) {

}
