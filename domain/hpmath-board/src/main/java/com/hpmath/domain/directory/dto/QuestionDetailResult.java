package com.hpmath.domain.directory.dto;

import com.hpmath.client.member.MemberClient;
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
    public static QuestionDetailResult from(Question question, MemberClient memberClient, final Long viewCount) {
        return new QuestionDetailResult(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getSolved(),
                viewCount,
                question.getRegisteredDateTime(),
                MemberDetailResult.from(memberClient.getMemberDetail(question.getOwnerMemberId())),
                MemberDetailResult.from(memberClient.getMemberDetail(question.getTargetMemberId())),
                question.getComments().stream()
                        .map(comment -> CommentDetailResult.from(comment, memberClient))
                        .toList(),
                question.getImages().stream()
                        .map(ImageUrlResult::from)
                        .toList()
        );
    }
}
