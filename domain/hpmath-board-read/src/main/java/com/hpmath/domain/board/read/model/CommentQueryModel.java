package com.hpmath.domain.board.read.model;

import com.hpmath.client.board.comment.BoardCommentClient.CommentDetail;
import com.hpmath.client.board.comment.BoardCommentClient.ImageUrlResult;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CommentQueryModel {
    private Long commentId;
    private String content;
    private Boolean selected;
    private List<String> images;
    private LocalDateTime registeredDateTime;
    private Long ownerId;

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
