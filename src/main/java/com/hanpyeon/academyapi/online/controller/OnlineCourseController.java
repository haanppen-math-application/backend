package com.hanpyeon.academyapi.online.controller;

import com.hanpyeon.academyapi.online.dto.AddOnlineCourseCommand;
import com.hanpyeon.academyapi.online.dto.AddOnlineCourseRequest;
import com.hanpyeon.academyapi.online.dto.DeleteOnlineCourseCommand;
import com.hanpyeon.academyapi.online.dto.OnlineCourseInfoUpdateCommand;
import com.hanpyeon.academyapi.online.dto.OnlineCourseInfoUpdateRequest;
import com.hanpyeon.academyapi.online.dto.OnlineCourseStudentUpdateCommand;
import com.hanpyeon.academyapi.online.dto.OnlineCourseStudentsUpdateRequest;
import com.hanpyeon.academyapi.online.service.OnlineCourseService;
import com.hanpyeon.academyapi.online.service.OnlineCourseUpdateService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
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
@RequestMapping("/api/online-courses")
@RequiredArgsConstructor
@Tag(name = "ONLINE COURSE")
class OnlineCourseController {
    private final OnlineCourseService onlineCourseService;
    private final OnlineCourseUpdateService onlineCourseUpdateService;

    @PostMapping
    public ResponseEntity<?> addNewOnlineCourse(
            @RequestBody @Validated final AddOnlineCourseRequest addOnlineCourseRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final AddOnlineCourseCommand addOnlineCourseCommand = addOnlineCourseRequest.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        onlineCourseService.addOnlineCourse(addOnlineCourseCommand);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{courseId}/info")
    public ResponseEntity<?> updateOnlineCourseInfo(
            @PathVariable(required = true) final Long courseId,
            @RequestBody @Validated final OnlineCourseInfoUpdateRequest onlineCourseInfoUpdateRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand = onlineCourseInfoUpdateRequest.toCommand(courseId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineCourseUpdateService.changeOnlineCourseInfo(onlineCourseUpdateCommand);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{courseId}/students")
    public ResponseEntity<?> updateOnlineCourseInfo(
            @PathVariable(required = true) final Long courseId,
            @RequestBody @Validated final OnlineCourseStudentsUpdateRequest onlineCourseStudentsUpdateRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final OnlineCourseStudentUpdateCommand onlineCourseStudentUpdateCommand = onlineCourseStudentsUpdateRequest.toCommand(
                memberPrincipal.memberId(), courseId);
        onlineCourseUpdateService.changeOnlineCourseStudents(onlineCourseStudentUpdateCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(
            @PathVariable(required = true) Long courseId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final DeleteOnlineCourseCommand deleteOnlineCourseCommand = new DeleteOnlineCourseCommand(courseId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineCourseUpdateService.deleteOnlineCourse(deleteOnlineCourseCommand);
        return ResponseEntity.ok().build();
    }
}
