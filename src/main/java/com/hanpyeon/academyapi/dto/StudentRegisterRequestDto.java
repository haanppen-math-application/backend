package com.hanpyeon.academyapi.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.NumberFormat;

public record StudentRegisterRequestDto(
        @NotBlank(message = "studentName") String studentName,
        @NotNull @Min(0) @Max(11) Integer studentGrade,
        @NotBlank @Pattern(regexp = "^[0-9]+$") String studentPhoneNumber
) {}
