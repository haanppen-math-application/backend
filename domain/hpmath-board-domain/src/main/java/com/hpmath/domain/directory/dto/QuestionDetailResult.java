package com.hpmath.domain.directory.dto;

import com.hpmath.domain.directory.entity.Question;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record QuestionDetailResult(
        Long questionId,
        String title,
        String content,
        Boolean solved,
        Long viewCount,
        LocalDateTime registeredDateTime,
        MemberDetailResult registeredMember,
        MemberDetailResult targetMember,
        List<CommentDetailResult> comments,
        List<ImageUrlResult> imageUrls
) {
    public static QuestionDetailResult from(Question question) {
        return new QuestionDetailResult(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getSolved(),
                question.getViewCount(),
                question.getRegisteredDateTime(),
                MemberDetailResult.from(question.getOwnerMember()),
                MemberDetailResult.from(question.getTargetMember()),
                question.getComments().stream()
                        .map(CommentDetailResult::from)
                        .toList(),
                question.getImages().stream()
                        .map(ImageUrlResult::from)
                        .toList()
        );
    }
}
