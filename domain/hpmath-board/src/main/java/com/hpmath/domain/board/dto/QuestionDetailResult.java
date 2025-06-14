package com.hpmath.domain.board.dto;

import com.hpmath.client.board.comment.BoardCommentClient.CommentDetail;
import com.hpmath.domain.board.MemberManager;
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
    public static QuestionDetailResult from(Question question, MemberManager memberManager, final Long viewCount, final List<CommentDetail> commentDetails) {
        return new QuestionDetailResult(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getSolved(),
                viewCount,
                question.getRegisteredDateTime(),
                memberManager.load(question.getOwnerMemberId()),
                memberManager.load(question.getTargetMemberId()),
                commentDetails.stream()
                        .map(comment -> CommentDetailResult.from(comment, memberManager))
                        .toList(),
                question.getImages().stream()
                        .map(QuestionImage::getImageSrc)
                        .map(ImageUrlResult::from)
                        .toList()
        );
    }
}
