package com.hanpyeon.academyapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDto(
        @NotBlank
        String userName,
        @NotBlank
        String userPhoneNumber,
        String grade,
        String password
) {
}
