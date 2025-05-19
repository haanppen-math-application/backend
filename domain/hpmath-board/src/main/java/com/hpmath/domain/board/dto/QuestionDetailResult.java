package com.hpmath.domain.board.dto;

import com.hpmath.client.board.comment.BoardCommentClient.CommentDetails;
import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.board.entity.Question;
import com.hpmath.domain.board.entity.QuestionImage;
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
    public static QuestionDetailResult from(Question question, MemberClient memberClient, final Long viewCount, final
                                            CommentDetails commentDetails) {
        return new QuestionDetailResult(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getSolved(),
                viewCount,
                question.getRegisteredDateTime(),
                MemberDetailResult.from(memberClient.getMemberDetail(question.getOwnerMemberId())),
                MemberDetailResult.from(memberClient.getMemberDetail(question.getTargetMemberId())),
                commentDetails.commentDetails().stream()
                        .map(comment -> CommentDetailResult.from(comment, memberClient))
                        .toList(),
                question.getImages().stream()
                        .map(QuestionImage::getImageSrc)
                        .map(ImageUrlResult::from)
                        .toList()
        );
    }
}
