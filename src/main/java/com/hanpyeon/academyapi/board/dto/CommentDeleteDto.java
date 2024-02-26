package com.hanpyeon.academyapi.board.dto;

import lombok.Builder;

@Builder
public record CommentDeleteDto (
        Long commentId,
        Long requestMemberId
){
}
