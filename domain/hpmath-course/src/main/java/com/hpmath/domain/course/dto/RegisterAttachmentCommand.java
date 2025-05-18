package com.hpmath.domain.course.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

public record RegisterAttachmentCommand(
        @Nonnull Long requestMemberId,
        @Nonnull Long memoMediaId,
        @NotBlank String mediaSrc
){
}
