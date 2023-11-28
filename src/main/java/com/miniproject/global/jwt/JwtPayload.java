package com.miniproject.global.jwt;

import java.util.Date;

public record JwtPayload(
        String email,
        Date issuedAt
) {
}
