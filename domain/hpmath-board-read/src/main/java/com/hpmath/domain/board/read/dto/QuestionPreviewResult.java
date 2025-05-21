package com.hpmath.domain.board.read.dto;

import com.hpmath.client.board.question.BoardQuestionClient.QuestionDetailInfo;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import java.time.LocalDateTime;

public record QuestionPreviewResult(
        Long questionId,
        String title,
        LocalDateTime registeredDateTime,
        Boolean solved,
        Integer commentCount,
        Long viewCount,
        MemberDetailResult owner,
        MemberDetailResult target
) {
    public static QuestionPreviewResult from(QuestionQueryModel model, Integer commentCount, Long viewCount, MemberDetailResult owner, MemberDetailResult target) {
        return new QuestionPreviewResult(
                model.questionId(),
                model.title(),
                model.registeredDateTime(),
                model.solved(),
                commentCount,
                viewCount,
                owner,
                target
        );
    }

    public static QuestionPreviewResult from(QuestionDetailInfo info, Integer commentCount, Long viewCount, MemberDetailResult owner, MemberDetailResult target) {
        return new QuestionPreviewResult(
                info.questionId(),
                info.title(),
                info.registeredDateTime(),
                info.solved(),
                commentCount,
                viewCount,
                owner,
                target
        );
    }
}
