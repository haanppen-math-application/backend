package com.hpmath.app.api.board.controller;

import com.hpmath.common.Role;
import com.hpmath.domain.board.dto.QuestionDeleteCommand;
import com.hpmath.domain.board.dto.QuestionRegisterCommand;
import com.hpmath.domain.board.dto.QuestionUpdateCommand;
import com.hpmath.domain.board.service.question.validate.QuestionContentConstraint;
import com.hpmath.domain.board.service.question.validate.QuestionImageConstraint;
import com.hpmath.domain.board.service.question.validate.QuestionTitleConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

class Requests {
    record QuestionDeleteRequest(
            @NotNull
            Long questionId
    ){
        QuestionDeleteCommand toCommand(final Long requestMemberId, final Role role) {
            return new QuestionDeleteCommand(questionId(), role, requestMemberId);
        }
    }

    @Schema(description = "질문 작성 API")
    record QuestionRegisterRequest(
            @NotNull
            Long targetMemberId,
            @QuestionTitleConstraint
            String title,
            @QuestionContentConstraint
            String content,
            @QuestionImageConstraint
            List<String> images
    ) {
        QuestionRegisterCommand toCommand(final Long requestMemberId, final Role role) {
            return new QuestionRegisterCommand(requestMemberId, targetMemberId(), role, title(), content(), images());
        }
    }

    record QuestionUpdateRequest(
            @NotNull
            Long questionId,
            @QuestionTitleConstraint
            String title,
            @QuestionContentConstraint
            String content,
            Long targetMemberId,
            @QuestionImageConstraint
            List<String> imageSources
    ) {
        QuestionUpdateCommand toCommand(final Long requestMemberId, final Role role) {
            return new QuestionUpdateCommand(questionId(), targetMemberId(), requestMemberId, role, content(), title(),
                    imageSources());
        }
    }
}
