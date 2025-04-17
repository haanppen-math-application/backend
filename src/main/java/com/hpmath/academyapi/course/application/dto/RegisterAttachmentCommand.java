package com.hpmath.academyapi.course.application.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

public record RegisterAttachmentCommand(
        @Nonnull Long requestMemberId,
        @Nonnull Long memoMediaId,
        @NotBlank String mediaSrc
){
}
