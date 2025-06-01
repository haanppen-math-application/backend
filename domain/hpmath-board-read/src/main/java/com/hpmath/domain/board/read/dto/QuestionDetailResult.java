package com.hpmath.domain.board.read.dto;

import com.hpmath.domain.board.read.model.MemberQueryModel;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import java.time.LocalDateTime;
import java.util.List;

public record QuestionDetailResult(
        Long questionId,
        String title,
        String content,
        LocalDateTime registeredDateTime,
        Boolean solved,
        List<CommentDetailResult> comments,
        List<String> imageUrls,
        Long viewCount,
        MemberDetailResult owner,
        MemberDetailResult target
) {
    public static QuestionDetailResult from(
            final QuestionQueryModel questionQueryModel,
            final List<CommentDetailResult> comments,
            final Long viewCount,
            final MemberQueryModel owner,
            final MemberQueryModel target
    ) {
        return new QuestionDetailResult(
                questionQueryModel.getQuestionId(),
                questionQueryModel.getTitle(),
                questionQueryModel.getContent(),
                questionQueryModel.getRegisteredDateTime(),
                questionQueryModel.getSolved(),
                comments,
                questionQueryModel.getMediaSrcs(),
                viewCount,
                MemberDetailResult.from(owner),
                MemberDetailResult.from(target)
        );
    }
}
