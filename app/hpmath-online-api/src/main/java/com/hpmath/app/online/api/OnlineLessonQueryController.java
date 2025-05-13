package com.hpmath.app.online.api;

import com.hpmath.domain.online.dto.OnlineLessonDetail;
import com.hpmath.domain.online.dto.OnlineLessonQueryCommand;
import com.hpmath.domain.online.service.lesson.OnlineLessonQueryService;
import com.hpmath.hpmathwebcommon.authentication.MemberPrincipal;
import com.hpmath.hpmathwebcommon.authenticationV2.Authorization;
import com.hpmath.hpmathwebcommon.authenticationV2.LoginInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "온라인 수업")
@SecurityRequirement(name = "jwtAuth")
public class OnlineLessonQueryController {
    private final OnlineLessonQueryService onlineLessonQueryService;

    @GetMapping("/api/online-courses/lesson/{lessonId}")
    @Operation(summary = "온라인 수업 상세 정보 조회 API")
    @Authorization(opened = true)
    public ResponseEntity<OnlineLessonDetail> queryDetails(
            @PathVariable(required = true) final Long lessonId,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final OnlineLessonQueryCommand command = new OnlineLessonQueryCommand(lessonId, memberPrincipal.memberId(), memberPrincipal.role());
        return ResponseEntity.ok(onlineLessonQueryService.loadDetails(command));
    }
}
