package com.hpmath.academyapi.board.dto;

import com.hpmath.academyapi.security.Role;
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
