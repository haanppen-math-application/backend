package com.hanpyeon.academyapi.board.dto;

import com.hanpyeon.academyapi.media.dto.ImageUrlDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

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
