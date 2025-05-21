package com.hpmath.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionInfoResult(
        Long questionId,
        String title,
        String content,
        Boolean solved,
        LocalDateTime registeredDateTime,
        Long ownerId,
        Long targetId,
        List<String> mediaSrcs
) {
    public static QuestionInfoResult mapToInfo (final List<QuestionInfo> results) {
        final List<String> mediaSrcs = results.stream()
                .map(QuestionInfo::getImageSrc)
                .toList();
        return QuestionInfoResult.from(results.get(0), mediaSrcs);
    }

    private static QuestionInfoResult from(QuestionInfo result, final List<String> mediaSrcs) {
        return new QuestionInfoResult(
                result.getQuestionId(),
                result.getTitle(),
                result.getContent(),
                result.getSolved(),
                result.registeredDateTime(),
                result.getOwnerId(),
                result.getTargetId(),
                mediaSrcs
        );
    }
}
