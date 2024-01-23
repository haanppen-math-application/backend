package com.hanpyeon.academyapi.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

@Builder
public record RegisterRequestDto(
        @NotBlank
        String userName,
        @NotBlank
        @Pattern(regexp = "^[0-9]+$")
        String userPhoneNumber,
        @Range(min = 0, max = 11)
        @Nullable
        Integer grade,
        String password
) {
}
