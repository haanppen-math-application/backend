package com.hanpyeon.academyapi.course.controller;

import com.hanpyeon.academyapi.course.application.dto.CourseRegisterCommand;
import com.hanpyeon.academyapi.course.application.dto.CourseUpdateCommand;
import com.hanpyeon.academyapi.course.application.dto.DeleteCourseCommand;
import com.hanpyeon.academyapi.course.application.dto.RegisterStudentCommand;
import com.hanpyeon.academyapi.course.application.dto.UpdateCourseStudentsCommand;
import com.hanpyeon.academyapi.course.application.port.in.AddStudentToCourseUseCase;
import com.hanpyeon.academyapi.course.application.port.in.CourseRegisterUseCase;
import com.hanpyeon.academyapi.course.application.port.in.DeleteCourseUseCase;
import com.hanpyeon.academyapi.course.application.port.in.UpdateCourseStudentsUseCase;
import com.hanpyeon.academyapi.course.application.port.in.UpdateCourseUseCase;
import com.hanpyeon.academyapi.course.controller.Requests.CourseUpdateRequest;
import com.hanpyeon.academyapi.course.controller.Requests.DeleteCourseRequest;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final AddStudentToCourseUseCase addStudentToCourseUseCase;
    private final DeleteCourseUseCase deleteCourseAdapter;
    private final CourseRegisterUseCase courseRegisterUseCase;
    private final UpdateCourseUseCase updateCourseNameUseCase;
    private final UpdateCourseStudentsUseCase updateCourseStudentsUseCase;

    @PostMapping(value = "/api/manage/courses/students", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반에 학생 추가 API", description = "존재하는 반에 학생들을 등록하는 기능입니다. 중복되는 학생들은 중복되어 등록되지 않습니다.")
    public ResponseEntity<?> addStudent(
            @Valid @RequestBody final Requests.RegisterStudentRequest registerStudentRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal) {

        final RegisterStudentCommand registerStudentDto = registerStudentRequest.toCommand(
                memberPrincipal.memberId()
        );
        addStudentToCourseUseCase.addStudentToCourse(registerStudentDto);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/api/manage/courses/{courseId}")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반을 삭제하는 API 입니다", description = "반을 삭제하는 기능은 원장님 권한 이상 필요")
    public ResponseEntity<?> delete(
            @ModelAttribute final DeleteCourseRequest deleteCourseRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final DeleteCourseCommand deleteCommand = deleteCourseRequest.toCommand(memberPrincipal);
        deleteCourseAdapter.delete(deleteCommand);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/api/manage/courses", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반 등록 API", description = "반을 등록하기 위한 API 입니다, 반 등록은 학생은 허용되지 않습니다.")
    public ResponseEntity<?> registerCourse(
            @Valid @RequestBody final Requests.CourseRegisterRequest courseRegisterRequestDto,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
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
    public ResponseEntity<?> updateCourseName(
            final @PathVariable Long courseId,
            final @RequestBody CourseUpdateRequest courseUpdateRequest,
            final @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final CourseUpdateCommand courseUpdateDto = courseUpdateRequest.toCommand(courseId, memberPrincipal.memberId());
        updateCourseNameUseCase.updateCourse(courseUpdateDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/course/{courseId}/students")
    @Operation(summary = "반 학생 업데이트를 위한 API")
    public ResponseEntity<?> deleteStudents(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                            @PathVariable Long courseId,
                                            @RequestBody Requests.UpdateCourseStudentsRequest updateCourseStudentsRequest
    ) {
        final UpdateCourseStudentsCommand updateCourseStudentsDto = updateCourseStudentsRequest.toCommand(memberPrincipal.memberId(), courseId);
        updateCourseStudentsUseCase.updateStudents(updateCourseStudentsDto);
        return ResponseEntity.ok(null);
    }

}
