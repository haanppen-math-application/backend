package com.hpmath.domain.directory.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CommentRegisterCommand(
        @NotNull
        Long questionId,
        @NotNull
        Long memberId,
        String content,
        List<String> images
){
}
