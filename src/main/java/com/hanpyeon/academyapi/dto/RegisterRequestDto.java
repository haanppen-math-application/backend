package com.hanpyeon.academyapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequestDto(
        @NotBlank
        String userName,
        @NotBlank
        String userPhoneNumber,
        Integer grade,
        String password
) {
}
