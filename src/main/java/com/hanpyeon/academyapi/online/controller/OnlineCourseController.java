package com.hanpyeon.academyapi.online.controller;

import com.hanpyeon.academyapi.online.OnlineCourseService;
import com.hanpyeon.academyapi.online.dto.AddOnlineCourseCommand;
import com.hanpyeon.academyapi.online.dto.AddOnlineCourseRequest;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class OnlineCourseController {
    private final OnlineCourseService onlineCourseService;

    @PostMapping("/api/online-courses")
    public ResponseEntity<?> addNewOnlineCourse(
            @Validated final AddOnlineCourseRequest addOnlineCourseRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final AddOnlineCourseCommand addOnlineCourseCommand = addOnlineCourseRequest.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        onlineCourseService.addOnlineCourse(addOnlineCourseCommand);

        return ResponseEntity.ok().build();
    }
}
