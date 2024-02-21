package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;

public record CommentSelectionUpdateRequestDto (
        @NotNull
        Long commentId,
        @NotNull
        Boolean state
){
}
