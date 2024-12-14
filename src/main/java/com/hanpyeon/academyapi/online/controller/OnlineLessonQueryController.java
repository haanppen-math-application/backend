package com.hanpyeon.academyapi.online.controller;

import com.hanpyeon.academyapi.online.dto.OnlineLessonDetail;
import com.hanpyeon.academyapi.online.dto.OnlineLessonQueryCommand;
import com.hanpyeon.academyapi.online.service.lesson.OnlineLessonQueryService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OnlineLessonQueryController {
    private final OnlineLessonQueryService onlineLessonQueryService;

    @GetMapping("/api/online-courses/lesson/{lessonId}")
    public ResponseEntity<OnlineLessonDetail> queryDetails(
            @PathVariable(required = true) final Long lessonId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final OnlineLessonQueryCommand command = new OnlineLessonQueryCommand(lessonId, memberPrincipal.memberId(), memberPrincipal.role());
        return ResponseEntity.ok(onlineLessonQueryService.loadDetails(command));
    }
}
