package com.hanpyeon.academyapi.course.controller;

import com.hanpyeon.academyapi.course.application.dto.AttachmentChunkResult;
import com.hanpyeon.academyapi.course.application.dto.DeleteAttachmentCommand;
import com.hanpyeon.academyapi.course.application.dto.DeleteMemoMediaCommand;
import com.hanpyeon.academyapi.course.application.dto.RegisterAttachmentCommand;
import com.hanpyeon.academyapi.course.application.dto.RegisterMemoMediaCommand;
import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
import com.hanpyeon.academyapi.course.application.port.in.DeleteAttachmentUseCase;
import com.hanpyeon.academyapi.course.application.port.in.DeleteMemoMediaUseCase;
import com.hanpyeon.academyapi.course.application.port.in.RegisterAttachmentUseCase;
import com.hanpyeon.academyapi.course.application.port.in.RegisterMemoMediaUseCase;
import com.hanpyeon.academyapi.course.application.port.in.UpdateMemoMediaUseCase;
import com.hanpyeon.academyapi.course.controller.Responses.RegisterAttachmentChunkResponse;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemoMediaController {
    private final DeleteAttachmentUseCase deleteAttachmentUseCase;
    private final DeleteMemoMediaUseCase deleteMemoMediaUseCase;
    private final RegisterAttachmentUseCase registerAttachmentUseCase;
    private final UpdateMemoMediaUseCase updateMemoMediaUseCase;
    private final RegisterMemoMediaUseCase registerMemoMediaUseCase;

    @DeleteMapping("/api/courses/memos/media/attachment/{targetAttachmentId}")
    public ResponseEntity<?> deleteAttachment(
            @PathVariable final Long targetAttachmentId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final DeleteAttachmentCommand command = DeleteAttachmentCommand.of(targetAttachmentId, memberPrincipal.memberId());
        deleteAttachmentUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/course/memo/{memoId}/media/{memoMediaId}")
    public ResponseEntity<?> deleteMemoMedia(
            @PathVariable Long memoMediaId,
            @PathVariable Long memoId,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final DeleteMemoMediaCommand command = DeleteMemoMediaCommand.of(memoMediaId, memoId, memberPrincipal.memberId());
        deleteMemoMediaUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/api/courses/memos/media/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> registerAttachment(
            @ModelAttribute @Valid final Requests.RegisterAttachmentWithChunkRequest request,
            @AuthenticationPrincipal @Nonnull final MemberPrincipal memberPrincipal
    ) {
        final RegisterAttachmentCommand command = request.toCommand(memberPrincipal.memberId());
        registerAttachmentUseCase.register(command);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/course/memo/media")
    public ResponseEntity<?> addMemoMedia(
            @RequestBody @Valid final Requests.RegisterMemoMediaRequest request,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final RegisterMemoMediaCommand command = request.toCommand(memberPrincipal.memberId());
        registerMemoMediaUseCase.register(command);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/api/course/memo/media")
    @Operation(summary = "메모에 관련된 영상을 순서 업데이트 api", description = "요청 시 순서에 맞게 미디어 파일을 보내야 합니다")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> updateMemoMedia(
            @RequestBody @Valid Requests.UpdateMemoMediaRequest updateMemoMediaRequest,
            @AuthenticationPrincipal @Nonnull final Long requestMemberId
    ) {
        final UpdateMediaMemoCommand command = updateMemoMediaRequest.toCommand(requestMemberId);
        updateMemoMediaUseCase.updateMediaMemo(command);
        return ResponseEntity.ok().build();
    }
}
