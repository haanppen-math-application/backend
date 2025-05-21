package com.hpmath.domain.board.read.model;

import com.hpmath.client.board.comment.BoardCommentClient.CommentDetail;
import com.hpmath.client.board.question.BoardQuestionClient.QuestionDetailInfo;
import java.time.LocalDateTime;
import java.util.List;

public record QuestionQueryModel(
        Long questionId,
        String title,
        String content,
        LocalDateTime registeredDateTime,
        List<String> mediaSrcs,
        Boolean solved,
        List<CommentQueryModel> comments,
        Long ownerMemberId,
        Long targetMemberId
) {
    public static QuestionQueryModel of(
            final QuestionDetailInfo questionInfo,
            final List<CommentDetail> comments,
            final Long ownerMemberId,
            final Long targetMemberId
    ) {
        return new QuestionQueryModel(
                questionInfo.questionId(),
                questionInfo.title(),
                questionInfo.content(),
                questionInfo.registeredDateTime(),
                questionInfo.mediaSrcs(),
                questionInfo.solved(),
                comments.stream()
                        .map(CommentQueryModel::of)
                        .toList(),
                ownerMemberId,
                targetMemberId
        );
    }
}
