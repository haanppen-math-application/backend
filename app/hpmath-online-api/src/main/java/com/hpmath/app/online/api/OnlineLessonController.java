package com.hpmath.app.online.api;

import com.hpmath.domain.online.dto.AddOnlineVideoCommand;
import com.hpmath.domain.online.dto.DeleteOnlineCourseVideoCommand;
import com.hpmath.domain.online.dto.DeleteOnlineVideoAttachmentCommand;
import com.hpmath.domain.online.dto.OnlineLessonInitializeCommand;
import com.hpmath.domain.online.dto.OnlineVideoPreviewUpdateCommand;
import com.hpmath.domain.online.dto.RegisterOnlineVideoAttachmentCommand;
import com.hpmath.domain.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.domain.online.dto.UpdateOnlineVideoSequenceCommand;
import com.hpmath.domain.online.service.lesson.OnlineAttachmentDeleteService;
import com.hpmath.domain.online.service.lesson.OnlineAttachmentRegisterService;
import com.hpmath.domain.online.service.lesson.OnlineLessonUpdateService;
import com.hpmath.domain.online.service.lesson.OnlineVideoDeleteService;
import com.hpmath.domain.online.service.lesson.OnlineVideoRegisterService;
import com.hpmath.domain.online.service.lesson.OnlineVideoSequenceUpdateService;
import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/online-courses/lesson")
@RequiredArgsConstructor
@Tag(name = "온라인 수업")
@SecurityRequirement(name = "jwtAuth")
class OnlineLessonController {
    private final OnlineLessonUpdateService onlineLessonUpdateService;
    private final OnlineVideoDeleteService onlineVideoDeleteService;
    private final OnlineVideoRegisterService onlineVideoRegisterService;
    private final OnlineVideoSequenceUpdateService onlineVideoSequenceUpdateService;
    private final OnlineAttachmentRegisterService onlineAttachmentRegisterService;
    private final OnlineAttachmentDeleteService onlineAttachmentDeleteService;

    @PutMapping
    @Operation(summary = "온라인 수업의 대표 정보를 수정하는 API 입니다", description = "필드를 null로 보내면, 해당 필드는 수정하지 않습니다.")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> updateOnlineLessonInfo(
            @Valid @RequestBody final Request.UpdateOnlineLessonInfoRequest updateOnlineLessonInfoRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand = updateOnlineLessonInfoRequest.toCommand(
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        onlineLessonUpdateService.updateLessonInfo(updateOnlineLessonInfoCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{lessonId}")
    @Operation(summary = "온라인 수업의 내용을 초기화 API", description = "등록된 영상 및 첨부파일, 수업 제목, 수업 범위, 수업 내용 삭제")
    @Authorization(values = Role.ADMIN)
    public ResponseEntity<Void> initializeLesson(
            @PathVariable(required = true) final Long lessonId,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final OnlineLessonInitializeCommand command = new OnlineLessonInitializeCommand(lessonId, memberPrincipal.memberId(), memberPrincipal.role());
        onlineLessonUpdateService.initializeCourse(command);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/videos/sequence")
    @Operation(summary = "온라인 수업 영상 순서 수정 API")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> changeSequence(
            @Valid @RequestBody final Request.UpdateOnlineVideoSequenceRequest request,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final UpdateOnlineVideoSequenceCommand command = request.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        onlineVideoSequenceUpdateService.updateSequence(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/videos")
    @Operation(summary = "온라인 수업에 영상 등록 API")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> updateOnlineCourseVideos(
            @Valid @RequestBody final Request.AddOnlineCourseVideoRequest addOnlineCourseVideosRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final AddOnlineVideoCommand addOnlineVideoCommand = addOnlineCourseVideosRequest.toCommand(
                memberPrincipal.memberId(),
                memberPrincipal.role());
        onlineVideoRegisterService.addOnlineVideo(addOnlineVideoCommand);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/videos")
    @Operation(summary = "온라인 수업 영상 Preview 옵션 수정 API")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> updateOnlineVideoPreviewStatus(
            @Valid @RequestBody final Request.OnlineVideoPreviewUpdateRequest request,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final OnlineVideoPreviewUpdateCommand command = request.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        onlineLessonUpdateService.updatePreviewStauts(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{onlineCourseId}/videos/{onlineVideoId}")
    @Operation(summary = "온라인 수업 영상 삭제 API", description = "온라인 수업 영상을 삭제, 첨부자료까지 모두 삭제")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> deleteOnlineCourseVideo(
            @PathVariable final Long onlineCourseId,
            @PathVariable final Long onlineVideoId,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final DeleteOnlineCourseVideoCommand deleteOnlineCourseVideoCommand = new DeleteOnlineCourseVideoCommand(
                onlineCourseId, onlineVideoId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineVideoDeleteService.deleteOnlineVideo(deleteOnlineCourseVideoCommand);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{onlineCourseId}/videos/{onlineVideoId}/attachments")
    @Operation(summary = "온라인 영상 첨부자료 추가 API", description = "첨부자료 추가")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> registerAttachment(
            @PathVariable(required = true) final Long onlineCourseId,
            @PathVariable(required = true) final Long onlineVideoId,
            @Valid @RequestBody final Request.RegisterOnlineVideoAttachmentRequest registerOnlineVideoAttachmentRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final RegisterOnlineVideoAttachmentCommand command = registerOnlineVideoAttachmentRequest.toCommand(onlineCourseId, onlineVideoId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineAttachmentRegisterService.register(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{onlineCourseId}/videos/{onlineVideoId}/attachments/{attachmentId}")
    @Operation(summary = "온라인 영상 첨부자료 삭제 API")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable(required = true) final Long onlineCourseId,
            @PathVariable(required = true) final Long onlineVideoId,
            @PathVariable(required = true) final Long attachmentId,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final DeleteOnlineVideoAttachmentCommand command = new DeleteOnlineVideoAttachmentCommand(attachmentId, onlineVideoId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineAttachmentDeleteService.deleteAttachment(command);
        return ResponseEntity.noContent().build();
    }
}
