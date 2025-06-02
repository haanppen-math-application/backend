package com.hpmath.domain.member.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

public record RegisterMemberCommand(
        @NotBlank String name,
        @Range(min = 0, max = 11) Integer grade,
        @NotBlank @Pattern(regexp = "^[0-9]+$") String phoneNumber,
        @NotNull Role role,
        Password password
) {
}
