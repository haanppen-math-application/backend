package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.security.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

public class Requests {
    @Builder
    public record CommentDeleteRequest(
            Long commentId,
            Long requestMemberId,
            Role role
    ){
    }

    @Schema(description = "댓글 등록 DTO", requiredProperties = {"questionId"})
    public record CommentRegisterRequest(
            @NotNull Long questionId,
            String content,
            List<String> images
    ) {
    }

    @Schema(description = "댓글 수정 DTO")
    public record CommentUpdateRequest(
            @NotNull
            Long commentId,
            @Schema(description = "바뀐 전체 본문을 보내야 합니다.")
            String content,
            List<String> imageSources
    ) {
    }

    public record QuestionDeleteRequest(
            Long questionId
    ){
    }

    @Schema(description = "질문 작성 API")
    public record QuestionRegisterRequest(
            Long targetMemberId,
            String title,
            String content,
            List<String> images
    ) {
    }

    public record QuestionUpdateRequest(
            @NotNull
            Long questionId,
            String title,
            String content,
            Long targetMemberId,
            List<String> imageSources
    ) {
    }
}
