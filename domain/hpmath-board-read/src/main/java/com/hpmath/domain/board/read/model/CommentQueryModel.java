package com.hpmath.domain.board.read.model;

import com.hpmath.client.board.comment.BoardCommentClient.CommentDetail;
import com.hpmath.client.board.comment.BoardCommentClient.ImageUrlResult;
import java.time.LocalDateTime;
import java.util.List;

public record CommentQueryModel(
        Long commentId,
        String content,
        Boolean selected,
        List<String> images,
        LocalDateTime registeredDateTime,
        Long ownerId
) {
    public static CommentQueryModel of(final CommentDetail commentDetail) {
        return new CommentQueryModel(
                commentDetail.commentId(),
                commentDetail.content(),
                commentDetail.selected(),
                commentDetail.images().stream().map(ImageUrlResult::imageUrl).toList(),
                commentDetail.registeredDateTime(),
                commentDetail.ownerId()
        );
    }
}
