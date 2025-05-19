package com.hpmath.domain.board.comment.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RegisterCommentCommand(
        @NotNull
        Long questionId,
        @NotNull
        Long requestMemberId,
        @NotNull
        String content,
        @NotNull
        List<String> imageSrcs
){
}
