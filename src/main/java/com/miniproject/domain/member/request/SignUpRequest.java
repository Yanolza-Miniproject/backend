package com.miniproject.domain.member.request;

import jakarta.validation.constraints.*;

public record SignUpRequest(

        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String nickname,
        @NotBlank String phoneNumber
) {
}
