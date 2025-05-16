package com.hpmath.domain.directory.dto;

import com.hpmath.hpmathcore.Role;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record QuestionUpdateCommand(
        @NotNull
        Long questionId,
        @NotNull
        Long targetMemberId,
        @NotNull
        Long requestMemberId,
        @NotNull
        Role memberRole,
        String content,
        String title,
        List<String> imageSources
) {
}
