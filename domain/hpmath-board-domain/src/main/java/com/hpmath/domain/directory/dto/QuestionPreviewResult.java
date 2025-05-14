package com.hpmath.domain.directory.dto;

import com.hpmath.client.member.MemberClient;
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
    public static QuestionPreviewResult from(Question question, MemberClient memberClient) {
        return new QuestionPreviewResult(
                question.getId(),
                question.getTitle(),
                question.getRegisteredDateTime(),
                question.getSolved(),
                question.getComments().size(),
                question.getViewCount(),
                MemberDetailResult.from(memberClient.getMemberDetail(question.getOwnerMemberId())),
                MemberDetailResult.from(memberClient.getMemberDetail(question.getTargetMemberId()))
        );
    }
}
