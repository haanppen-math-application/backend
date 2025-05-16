package com.hpmath.domain.board.dto;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.board.entity.Comment;
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
    public static CommentDetailResult from(Comment comment, MemberClient memberClient) {
        return new CommentDetailResult(
                comment.getId(),
                comment.getContent(),
                comment.getAdopted(),
                comment.getImages().stream()
                        .map(ImageUrlResult::from)
                        .toList(),
                comment.getRegisteredDateTime(),
                MemberDetailResult.from(memberClient.getMemberDetail(comment.getRegisteredMember()))
        );
    }
}
