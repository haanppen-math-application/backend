package com.hanpyeon.academyapi.online.controller;

import com.hanpyeon.academyapi.online.dto.AddOnlineCourseVideoRequest;
import com.hanpyeon.academyapi.online.dto.AddOnlineVideoCommand;
import com.hanpyeon.academyapi.online.dto.DeleteOnlineCourseVideoCommand;
import com.hanpyeon.academyapi.online.dto.RegisterOnlineVideoAttachmentCommand;
import com.hanpyeon.academyapi.online.dto.RegisterOnlineVideoAttachmentRequest;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoRequest;
import com.hanpyeon.academyapi.online.service.lesson.OnlineAttachmentRegisterService;
import com.hanpyeon.academyapi.online.service.lesson.OnlineLessonUpdateService;
import com.hanpyeon.academyapi.online.service.lesson.OnlineVideoDeleteService;
import com.hanpyeon.academyapi.online.service.lesson.OnlineVideoRegisterService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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
class OnlineLessonController {
    private final OnlineLessonUpdateService onlineLessonUpdateService;
    private final OnlineVideoDeleteService onlineVideoDeleteService;
    private final OnlineVideoRegisterService onlineVideoRegisterService;
    private final OnlineAttachmentRegisterService onlineAttachmentRegisterService;

    @PutMapping
    @Operation(summary = "온라인 수업의 대표 정보를 수정하는 API 입니다", description = "필드를 null로 보내면, 해당 필드는 수정하지 않습니다.")
    public ResponseEntity<?> updateOnlineLessonInfo(
            @Validated final UpdateOnlineLessonInfoRequest updateOnlineLessonInfoRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand = updateOnlineLessonInfoRequest.toCommand(
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        onlineLessonUpdateService.updateLessonInfo(updateOnlineLessonInfoCommand);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/videos")
    @Operation(summary = "온라인 수업에 영상 등록 API")
    public ResponseEntity<?> updateOnlineCourseVideos(
            @Validated final AddOnlineCourseVideoRequest addOnlineCourseVideosRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final AddOnlineVideoCommand addOnlineVideoCommand = addOnlineCourseVideosRequest.toCommand(
                memberPrincipal.memberId(),
                memberPrincipal.role());
        onlineVideoRegisterService.addOnlineVideo(addOnlineVideoCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{onlineCourseId}/videos/{onlineVideoId}")
    @Operation(summary = "온라인 수업 영상 삭제 API", description = "온라인 수업 영상을 삭제, 첨부자료까지 모두 삭제")
    public ResponseEntity<?> deleteOnlineCourseVideo(
            @PathVariable final Long onlineCourseId,
            @PathVariable final Long onlineVideoId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final DeleteOnlineCourseVideoCommand deleteOnlineCourseVideoCommand = new DeleteOnlineCourseVideoCommand(
                onlineCourseId, onlineVideoId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineVideoDeleteService.deleteOnlineVideo(deleteOnlineCourseVideoCommand);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{onlineCourseId}/videos/{onlineVideoId}/attachments")
    @Operation(summary = "온라인 영상 첨부자료 추가 API", description = "첨부자료 추가")
    public ResponseEntity<?> registerAttachment(
            @PathVariable(required = true) final Long onlineCourseId,
            @PathVariable(required = true) final Long onlineVideoId,
            @Validated @RequestBody final RegisterOnlineVideoAttachmentRequest registerOnlineVideoAttachmentRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final RegisterOnlineVideoAttachmentCommand command = registerOnlineVideoAttachmentRequest.toCommand(onlineCourseId, onlineVideoId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineAttachmentRegisterService.register(command);
        return ResponseEntity.ok().build();
    }
}
