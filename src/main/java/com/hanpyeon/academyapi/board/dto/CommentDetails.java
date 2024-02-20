package com.hanpyeon.academyapi.board.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CommentDetails(
        Long commentId,
        String content,
        Boolean selected,
        List<ImageUrlDto> images,
        LocalDateTime registeredDateTime,
        MemberDetails registeredMemberDetails
) {
}
