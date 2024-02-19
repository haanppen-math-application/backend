package com.hanpyeon.academyapi.board.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record QuestionDetails(
        boolean solved,
        Long viewCount,
        LocalDateTime registeredDateTime,
        Integer memberGrade,
        MemberDetails registeredMember,
        MemberDetails targetMember,
        List<CommentDetails> comments,
        List<ImageUrlDto> imageUrls)
{}
