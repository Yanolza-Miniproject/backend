package com.miniproject.domain.Member.request;

import jakarta.validation.constraints.*;

public record SignUpRequest(

        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank String phoneNumber
) {
}
