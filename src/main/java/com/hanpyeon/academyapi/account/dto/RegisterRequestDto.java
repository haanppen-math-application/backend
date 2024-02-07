package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

public record RegisterRequestDto(
        @NotBlank String name,
        @Range(min = 0, max = 11) Integer grade,
        @NotBlank @Pattern(regexp = "^[0-9]+$") String phoneNumber,
        @NotNull Role role,
        String password
) {
}
