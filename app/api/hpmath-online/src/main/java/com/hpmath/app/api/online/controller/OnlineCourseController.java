package com.hpmath.app.api.online.controller;

import com.hpmath.domain.online.dto.AddOnlineCourseCommand;
import com.hpmath.domain.online.dto.DeleteOnlineCourseCommand;
import com.hpmath.domain.online.dto.OnlineCourseInfoUpdateCommand;
import com.hpmath.domain.online.dto.OnlineCourseStudentUpdateCommand;
import com.hpmath.domain.online.service.course.OnlineCourseRegisterService;
import com.hpmath.domain.online.service.course.OnlineCourseUpdateService;
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
@RequestMapping("/api/online-courses")
@RequiredArgsConstructor
@Tag(name = "ONLINE COURSE")
@SecurityRequirement(name = "jwtAuth")
class OnlineCourseController {
    private final OnlineCourseRegisterService onlineCourseService;
    private final OnlineCourseUpdateService onlineCourseUpdateService;

    @PostMapping
    @Operation(summary = "새로운 온라인 강의 등록")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> addNewOnlineCourse(
            @RequestBody @Valid final Request.AddOnlineCourseRequest addOnlineCourseRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final AddOnlineCourseCommand addOnlineCourseCommand = addOnlineCourseRequest.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        onlineCourseService.addOnlineCourse(addOnlineCourseCommand);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{courseId}/info")
    @Operation(summary = "온라인 수업의 반 이름 / 담당 선생님 수정")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> updateOnlineCourseInfo(
            @PathVariable(required = true) final Long courseId,
            @RequestBody @Valid final Request.OnlineCourseInfoUpdateRequest onlineCourseInfoUpdateRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand = onlineCourseInfoUpdateRequest.toCommand(courseId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineCourseUpdateService.changeOnlineCourseInfo(onlineCourseUpdateCommand);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{courseId}/students")
    @Operation(summary = "온라인 강의의 학생 수정")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> updateOnlineCourseStudents(
            @PathVariable(required = true) final Long courseId,
            @RequestBody @Valid final Request.OnlineCourseStudentsUpdateRequest onlineCourseStudentsUpdateRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final OnlineCourseStudentUpdateCommand onlineCourseStudentUpdateCommand = onlineCourseStudentsUpdateRequest.toCommand(
                memberPrincipal.memberId(), courseId);
        onlineCourseUpdateService.changeOnlineCourseStudents(onlineCourseStudentUpdateCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "온라인 강의 삭제")
    @Authorization(values = {Role.ADMIN})
    public ResponseEntity<Void> deleteCourse(
            @PathVariable(required = true) Long courseId,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final DeleteOnlineCourseCommand deleteOnlineCourseCommand = new DeleteOnlineCourseCommand(courseId,
                memberPrincipal.memberId(), memberPrincipal.role());
        onlineCourseUpdateService.deleteOnlineCourse(deleteOnlineCourseCommand);
        return ResponseEntity.ok().build();
    }
}
