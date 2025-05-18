package com.hpmath.app.api.course.controller;

import com.hpmath.app.api.course.controller.Requests.CourseUpdateRequest;
import com.hpmath.app.api.course.controller.Requests.DeleteCourseRequest;
import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import com.hpmath.domain.course.dto.CourseRegisterCommand;
import com.hpmath.domain.course.dto.CourseUpdateCommand;
import com.hpmath.domain.course.dto.DeleteCourseCommand;
import com.hpmath.domain.course.dto.RegisterStudentCommand;
import com.hpmath.domain.course.dto.UpdateCourseStudentsCommand;
import com.hpmath.domain.course.service.AddStudentToCourseService;
import com.hpmath.domain.course.service.CourseRegisterService;
import com.hpmath.domain.course.service.DeleteCourseService;
import com.hpmath.domain.course.service.UpdateCourseService;
import com.hpmath.domain.course.service.UpdateCourseStudentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CourseController {
    private final AddStudentToCourseService addStudentToCourseUseCase;
    private final DeleteCourseService deleteCourseAdapter;
    private final CourseRegisterService courseRegisterUseCase;
    private final UpdateCourseService updateCourseNameUseCase;
    private final UpdateCourseStudentsService updateCourseStudentsUseCase;

    @PostMapping(value = "/api/manage/courses/students", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반에 학생 추가 API", description = "존재하는 반에 학생들을 등록하는 기능입니다. 중복되는 학생들은 중복되어 등록되지 않습니다.")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> addStudent(
            @Valid @RequestBody final Requests.RegisterStudentRequest registerStudentRequest,
            @LoginInfo final MemberPrincipal memberPrincipal) {

        final RegisterStudentCommand registerStudentDto = registerStudentRequest.toCommand(
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        addStudentToCourseUseCase.addStudentToCourse(registerStudentDto);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/api/manage/courses/{courseId}")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반을 삭제하는 API 입니다", description = "반을 삭제하는 기능은 원장님 권한 이상 필요")
    @Authorization(values = Role.ADMIN)
    public ResponseEntity<Void> delete(
            @ModelAttribute final DeleteCourseRequest deleteCourseRequest,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final DeleteCourseCommand deleteCommand = deleteCourseRequest.toCommand(memberPrincipal.role(), memberPrincipal.memberId());
        deleteCourseAdapter.delete(deleteCommand);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/api/manage/courses", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반 등록 API", description = "반을 등록하기 위한 API 입니다, 반 등록은 학생은 허용되지 않습니다.")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> registerCourse(
            @Valid @RequestBody final Requests.CourseRegisterRequest courseRegisterRequestDto,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final CourseRegisterCommand courseRegisterDto = courseRegisterRequestDto.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        final Long createdCourseId = courseRegisterUseCase.register(courseRegisterDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCourseId)
                .toUri()
        ).build();
    }

    @Operation(summary = "반 이름, 담당 선생님을 수정 API", description = "반 이름, 담당 선생님을 수정하는 API 입니다. 바꾸자 하는 담당자가 선생님이 아니라면, 에러가 발생합니다")
    @SecurityRequirement(name = "jwtAuth")
    @PutMapping(value = "/api/manage/courses/{courseId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> updateCourseName(
            final @PathVariable Long courseId,
            final @RequestBody CourseUpdateRequest courseUpdateRequest,
            final @LoginInfo MemberPrincipal memberPrincipal
    ) {
        final CourseUpdateCommand courseUpdateDto = courseUpdateRequest.toCommand(courseId, memberPrincipal.memberId());
        updateCourseNameUseCase.updateCourse(courseUpdateDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/course/{courseId}/students")
    @Operation(summary = "반 학생 업데이트를 위한 API")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> deleteStudents(@LoginInfo MemberPrincipal memberPrincipal,
                                            @PathVariable Long courseId,
                                            @RequestBody Requests.UpdateCourseStudentsRequest updateCourseStudentsRequest
    ) {
        final UpdateCourseStudentsCommand updateCourseStudentsDto = updateCourseStudentsRequest.toCommand(memberPrincipal.memberId(), courseId);
        updateCourseStudentsUseCase.updateStudents(updateCourseStudentsDto);
        return ResponseEntity.ok(null);
    }

}
