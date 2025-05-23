package com.hpmath.domain.board.read.model;

import com.hpmath.client.board.comment.BoardCommentClient.CommentDetail;
import com.hpmath.client.board.question.BoardQuestionClient.QuestionDetailInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionQueryModel {
    private Long questionId;
    private String title;
    private String content;
    private LocalDateTime registeredDateTime;
    private List<String> mediaSrcs;
    private Boolean solved;
    private List<CommentQueryModel> comments;
    private Long ownerMemberId;
    private Long targetMemberId;

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
