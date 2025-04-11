package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.media.dto.ImageUrlDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public class Responses {
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

    @Builder
    public record QuestionDetails(
            Long questionId,
            String title,
            String content,
            Boolean solved,
            Long viewCount,
            LocalDateTime registeredDateTime,
            MemberDetails registeredMember,
            MemberDetails targetMember,
            List<CommentDetails> comments,
            List<ImageUrlDto> imageUrls) {
    }

    @Builder
    public record MemberDetails(
            Long memberId,
            String memberName,
            Integer memberGrade,
            String role
    ) {
    }

    @Builder
    public record QuestionPreview(
            Long questionId,
            String title,
            LocalDateTime registeredDateTime,
            Boolean solved,
            Integer commentCount,
            Long viewCount,
            MemberDetails owner,
            MemberDetails target
    ) {
    }
}
