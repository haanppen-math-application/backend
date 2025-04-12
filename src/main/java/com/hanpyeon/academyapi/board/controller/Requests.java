package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.board.dto.CommentRegisterCommand;
import com.hanpyeon.academyapi.board.dto.CommentUpdateCommand;
import com.hanpyeon.academyapi.board.dto.QuestionDeleteCommand;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterCommand;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateCommand;
import com.hanpyeon.academyapi.board.service.comment.validate.CommentContentConstraint;
import com.hanpyeon.academyapi.board.service.comment.validate.CommentImageConstraint;
import com.hanpyeon.academyapi.board.service.question.validate.QuestionContentConstraint;
import com.hanpyeon.academyapi.board.service.question.validate.QuestionImageConstraint;
import com.hanpyeon.academyapi.board.service.question.validate.QuestionTitleConstraint;
import com.hanpyeon.academyapi.security.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

class Requests {
    @Schema(description = "댓글 등록 DTO", requiredProperties = {"questionId"})
    record CommentRegisterRequest(
            @NotNull
            Long questionId,
            @CommentContentConstraint
            String content,
            @CommentImageConstraint
            List<String> images
    ) {
        CommentRegisterCommand toCommand(final Long requestMemberId) {
            return new CommentRegisterCommand(questionId(), requestMemberId, content(), images());
        }
    }

    @Schema(description = "댓글 수정 DTO")
    record CommentUpdateRequest(
            @NotNull
            Long commentId,
            @Schema(description = "바뀐 전체 본문을 보내야 합니다.")
            @CommentContentConstraint
            String content,
            @CommentImageConstraint
            List<String> imageSources
    ) {
        CommentUpdateCommand toCommand(final Long requestMemberId, final Role role) {
            return new CommentUpdateCommand(requestMemberId, role, commentId(), content(), imageSources());
        }
    }

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
        QuestionRegisterCommand toCommand(final Long requestMemberId) {
            return new QuestionRegisterCommand(requestMemberId, targetMemberId(), title(), content(), images());
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
