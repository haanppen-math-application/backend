package com.hpmath.domain.directory.dto;

import com.hpmath.domain.directory.entity.Question;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record QuestionPreviewResult(
        Long questionId,
        String title,
        LocalDateTime registeredDateTime,
        Boolean solved,
        Integer commentCount,
        Long viewCount,
        MemberDetailResult owner,
        MemberDetailResult target
) {
    public static QuestionPreviewResult from(Question question) {
        return new QuestionPreviewResult(
                question.getId(),
                question.getTitle(),
                question.getRegisteredDateTime(),
                question.getSolved(),
                question.getComments().size(),
                question.getViewCount(),
                MemberDetailResult.from(question.getOwnerMember()),
                MemberDetailResult.from(question.getTargetMember())
        );
    }
}
