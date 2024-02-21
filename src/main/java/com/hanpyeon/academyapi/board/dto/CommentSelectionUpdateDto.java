package com.hanpyeon.academyapi.board.dto;

import lombok.Builder;

@Builder
public record CommentSelectionUpdateDto(
        Long commendId,
        Long requestMemberId,
        Boolean status
){
}
