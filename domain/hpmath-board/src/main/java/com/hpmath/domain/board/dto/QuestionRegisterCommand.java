package com.hpmath.domain.board.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record QuestionRegisterCommand(
        @NotNull
        Long requestMemberId,
        Long targetMemberId,
        @NotNull
        Role role,
        String title,
        String content,
        List<String> images
) {}
