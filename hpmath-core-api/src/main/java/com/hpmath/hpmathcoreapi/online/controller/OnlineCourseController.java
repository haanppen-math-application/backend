package com.hpmath.hpmathcoreapi.online.controller;

import com.hpmath.hpmathcoreapi.online.dto.AddOnlineCourseCommand;
import com.hpmath.hpmathcoreapi.online.dto.AddOnlineCourseRequest;
import com.hpmath.hpmathcoreapi.online.dto.DeleteOnlineCourseCommand;
import com.hpmath.hpmathcoreapi.online.dto.OnlineCourseInfoUpdateCommand;
import com.hpmath.hpmathcoreapi.online.dto.OnlineCourseInfoUpdateRequest;
import com.hpmath.hpmathcoreapi.online.dto.OnlineCourseStudentUpdateCommand;
import com.hpmath.hpmathcoreapi.online.dto.OnlineCourseStudentsUpdateRequest;
import com.hpmath.hpmathcoreapi.online.service.course.OnlineCourseRegisterService;
import com.hpmath.hpmathcoreapi.online.service.course.OnlineCourseUpdateService;
import com.hpmath.hpmathcoreapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "jwtAuth")
class OnlineCourseController {
    private final OnlineCourseRegisterService onlineCourseService;
    private final OnlineCourseUpdateService onlineCourseUpdateService;

    @PostMapping
    @Operation(summary = "새로운 온라인 강의 등록")
    public ResponseEntity<?> addNewOnlineCourse(
            @RequestBody @Validated final AddOnlineCourseRequest addOnlineCourseRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final AddOnlineCourseCommand addOnlineCourseCommand = addOnlineCourseRequest.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        onlineCourseService.addOnlineCourse(addOnlineCourseCommand);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{courseId}/info")
    @Operation(summary = "온라인 수업의 반 이름 / 담당 선생님 수정")
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
    @Operation(summary = "온라인 강의의 학생 수정")
    public ResponseEntity<?> updateOnlineCourseStudents(
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
    @Operation(summary = "온라인 강의 삭제")
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
