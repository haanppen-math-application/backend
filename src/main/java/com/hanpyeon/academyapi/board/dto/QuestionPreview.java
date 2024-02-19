package com.hanpyeon.academyapi.board.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record QuestionPreview (
        Long questionId,
        String content,
        Boolean solved,
        List<ImageUrlDto> images,
        Integer commentCount,
        Long viewCount,
        MemberDetails owner,
        MemberDetails target
){}
