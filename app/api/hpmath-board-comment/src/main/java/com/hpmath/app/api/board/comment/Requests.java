package com.hpmath.app.api.board.comment;

import com.hpmath.common.Role;
import com.hpmath.domain.board.comment.dto.RegisterCommentCommand;
import com.hpmath.domain.board.comment.dto.UpdateCommentCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

class Requests {
    record RegisterCommentRequest(
            @NotNull
            Long questionId,
            String content,
            List<String> images
    ) {
        RegisterCommentCommand toCommand(final Long requestMemberId) {
            return new RegisterCommentCommand(questionId(), requestMemberId, content(), images());
        }
    }

    @Schema(description = "댓글 수정 DTO")
    record UpdateCommentRequest(
            @NotNull
            Long commentId,
            @Schema(description = "바뀐 전체 본문을 보내야 합니다.")
            String content,
            List<String> imageSources
    ) {
        UpdateCommentCommand toCommand(final Long requestMemberId, final Role role) {
            return new UpdateCommentCommand(requestMemberId, role, commentId(), content(), imageSources());
        }
    }
}
