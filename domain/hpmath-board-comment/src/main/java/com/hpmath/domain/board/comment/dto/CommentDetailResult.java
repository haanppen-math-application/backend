package com.hpmath.domain.board.comment.dto;

import com.hpmath.client.member.MemberClient.MemberInfo;
import com.hpmath.domain.board.comment.entity.Comment;
import com.hpmath.domain.board.comment.entity.CommentImage;
import java.time.LocalDateTime;
import java.util.List;

public record CommentDetailResult(
        Long commentId,
        String content,
        Boolean selected,
        List<ImageUrlResult> images,
        LocalDateTime registeredDateTime,
        MemberDetailResult registeredMemberDetails
) {
    public static CommentDetailResult from(Comment comment, MemberInfo memberInfo) {
        return new CommentDetailResult(
                comment.getId(),
                comment.getContent(),
                true,
                comment.getImages().stream()
                        .map(CommentImage::getImageSrc)
                        .map(ImageUrlResult::new)
                        .toList(),
                comment.getRegisteredDateTime(),
                MemberDetailResult.from(memberInfo));
    }
}

