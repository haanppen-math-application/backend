package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record CommentRegisterCommand(
        @NotNull
        Long questionId,
        @NotNull
        Long memberId,
        String content,
        List<String> images
){
}
