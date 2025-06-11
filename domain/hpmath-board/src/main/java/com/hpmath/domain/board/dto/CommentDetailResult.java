package com.hpmath.domain.board.dto;

import com.hpmath.client.board.comment.BoardCommentClient;
import com.hpmath.client.board.comment.BoardCommentClient.CommentDetail;
import com.hpmath.domain.board.MemberManager;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record CommentDetailResult(
        Long commentId,
        String content,
        Boolean selected,
        List<ImageUrlResult> images,
        LocalDateTime registeredDateTime,
        MemberDetailResult registeredMemberDetails
) {
    public static CommentDetailResult from(CommentDetail comment, MemberManager memberManager) {
        return new CommentDetailResult(
                comment.commentId(),
                comment.content(),
                comment.selected(),
                comment.images().stream()
                        .map(BoardCommentClient.ImageUrlResult::imageUrl)
                        .map(ImageUrlResult::from)
                        .toList(),
                comment.registeredDateTime(),
                memberManager.load(comment.ownerId())
        );
    }
}
