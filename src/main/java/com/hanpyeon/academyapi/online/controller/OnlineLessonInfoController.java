package com.hanpyeon.academyapi.online.controller;

import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoRequest;
import com.hanpyeon.academyapi.online.service.lesson.OnlineLessonService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/online-courses/lesson")
@RequiredArgsConstructor
@Tag(name = "온라인 수업")
class OnlineLessonInfoController {
    private final OnlineLessonService onlineLessonService;

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
        onlineLessonService.updateLessonInfo(updateOnlineLessonInfoCommand);
        return ResponseEntity.ok().build();
    }
}
