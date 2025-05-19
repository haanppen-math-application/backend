package com.hpmath.domain.board.dto;

import com.hpmath.client.board.comment.BoardCommentClient;
import com.hpmath.client.board.comment.BoardCommentClient.CommentDetail;
import com.hpmath.client.member.MemberClient;
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
    public static CommentDetailResult from(CommentDetail comment, MemberClient memberClient) {
        return new CommentDetailResult(
                comment.commentId(),
                comment.content(),
                comment.selected(),
                comment.images().stream()
                        .map(BoardCommentClient.ImageUrlResult::imageUrl)
                        .map(ImageUrlResult::from)
                        .toList(),
                comment.registeredDateTime(),
                MemberDetailResult.from(memberClient.getMemberDetail(comment.ownerId()))
        );
    }
}
