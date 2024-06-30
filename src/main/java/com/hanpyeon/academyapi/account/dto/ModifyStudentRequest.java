package com.hanpyeon.academyapi.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ModifyStudentRequest(
        @NotNull
        Long studentId,
        @NotNull
        @NotBlank
        String name,
        @NotNull
        @NotBlank
        @Pattern(regexp = "^[0-9]+$")
        String phoneNumber,
        @NotNull
        Integer grade
) {
}
