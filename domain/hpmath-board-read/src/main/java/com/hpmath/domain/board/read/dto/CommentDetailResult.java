package com.hpmath.domain.board.read.dto;

import com.hpmath.domain.board.read.model.CommentQueryModel;
import java.time.LocalDateTime;
import java.util.List;

public record CommentDetailResult(
        Long commentId,
        String content,
        Boolean selected,
        List<String> images,
        LocalDateTime registeredDateTime,
        MemberDetailResult registeredMemberDetails
) {
    public static CommentDetailResult from(final CommentQueryModel model, final MemberDetailResult owner) {
        return new CommentDetailResult(
                model.getCommentId(),
                model.getContent(),
                model.getSelected(),
                model.getImages(),
                model.getRegisteredDateTime(),
                owner
        );
    }
}
