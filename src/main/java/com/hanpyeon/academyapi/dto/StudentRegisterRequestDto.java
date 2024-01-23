package com.hanpyeon.academyapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

@Builder
public record StudentRegisterRequestDto(
        @NotBlank
        String studentName,
        @NotBlank
        @Pattern(regexp = "^[0-9]+$")
        String studentPhoneNumber,
        @Range(min = 0, max = 11)
        @NotNull
        Integer studentGrade,
        String studentPassword
) {
}
