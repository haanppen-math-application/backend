package com.hpmath.app.api.course.controller;

import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import com.hpmath.domain.course.dto.DeleteAttachmentCommand;
import com.hpmath.domain.course.dto.DeleteMemoMediaCommand;
import com.hpmath.domain.course.dto.RegisterAttachmentCommand;
import com.hpmath.domain.course.dto.RegisterMemoMediaCommand;
import com.hpmath.domain.course.dto.UpdateMediaMemoCommand;
import com.hpmath.domain.course.service.DeleteAttachmentService;
import com.hpmath.domain.course.service.DeleteMemoMediaService;
import com.hpmath.domain.course.service.RegisterAttachmentService;
import com.hpmath.domain.course.service.RegisterMemoMediaService;
import com.hpmath.domain.course.service.UpdateMemoMediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final DeleteAttachmentService deleteAttachmentUseCase;
    private final DeleteMemoMediaService deleteMemoMediaUseCase;
    private final RegisterAttachmentService registerAttachmentUseCase;
    private final UpdateMemoMediaService updateMemoMediaUseCase;
    private final RegisterMemoMediaService registerMemoMediaUseCase;

    @DeleteMapping("/api/courses/memos/media/attachment/{targetAttachmentId}")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable final Long targetAttachmentId,
            @LoginInfo final MemberPrincipal loginId
    ) {
        final DeleteAttachmentCommand command = DeleteAttachmentCommand.of(targetAttachmentId, loginId.memberId(), loginId.role());
        deleteAttachmentUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/course/memo/{memoId}/media/{memoMediaId}")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> deleteMemoMedia(
            @PathVariable Long memoMediaId,
            @PathVariable Long memoId,
            @LoginInfo MemberPrincipal loginId
    ) {
        final DeleteMemoMediaCommand command = DeleteMemoMediaCommand.of(memoMediaId, memoId, loginId.memberId());
        deleteMemoMediaUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/api/courses/memos/media/attachment")
    @Operation
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> registerAttachment(
            @ModelAttribute @Valid final Requests.RegisterAttachmentWithChunkRequest request,
            @LoginInfo @Nonnull final MemberPrincipal loginId
    ) {
        final RegisterAttachmentCommand command = request.toCommand(loginId.memberId());
        registerAttachmentUseCase.register(command);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/course/memo/media")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> addMemoMedia(
            @RequestBody @Valid final Requests.RegisterMemoMediaRequest request,
            @LoginInfo MemberPrincipal loginId
    ) {
        final RegisterMemoMediaCommand command = request.toCommand(loginId.memberId());
        registerMemoMediaUseCase.register(command);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/api/course/memo/media")
    @Operation(summary = "메모에 관련된 영상을 순서 업데이트 api", description = "요청 시 순서에 맞게 미디어 파일을 보내야 합니다")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> updateMemoMedia(
            @RequestBody @Valid Requests.UpdateMemoMediaRequest updateMemoMediaRequest,
            @LoginInfo @Nonnull final MemberPrincipal memberPrincipal
    ) {
        final UpdateMediaMemoCommand command = updateMemoMediaRequest.toCommand(memberPrincipal.memberId());
        updateMemoMediaUseCase.updateMediaMemo(command);
        return ResponseEntity.ok().build();
    }
}
