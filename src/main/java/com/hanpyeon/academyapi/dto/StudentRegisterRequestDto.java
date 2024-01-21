package com.hanpyeon.academyapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

public record StudentRegisterRequestDto(
        @NotBlank String studentName,
        @NotNull @Range(min = 0, max = 11) Integer studentGrade,
        @NotBlank @Pattern(regexp = "^[0-9]+$") String studentPhoneNumber
) {
}
