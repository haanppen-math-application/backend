package com.hpmath.app.api.course.controller;

import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import com.hpmath.domain.course.service.CourseQueryService;
import com.hpmath.domain.course.dto.Responses.CourseDetailResponse;
import com.hpmath.domain.course.dto.Responses.CoursePreviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourseQueryController {
    private final CourseQueryService courseQueryService;

    @GetMapping("/api/courses")
    @Operation(summary = "전체 반을 조회하는 api 입니다", description = "로그인된 모든 사용자가 사용 가능한 API 입니다.")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(opened = true)
    public ResponseEntity<List<CoursePreviewResponse>> getAllCourses() {
        return ResponseEntity.ok(courseQueryService.loadAllCoursePreviews());
    }

    @GetMapping(value = "/api/courses/my")
    @Authorization(opened = true)
    public ResponseEntity<List<CoursePreviewResponse>> queryMyCourses(
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        return ResponseEntity.ok(courseQueryService.loadCoursePreviews(memberPrincipal.memberId()));
    }

    @GetMapping("/api/courses/students/{studentId}")
    @Operation(summary = "학생 ID 를 이용한 반 조회", description = "선생님, 원장님만 접근 가능합니다")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<List<CoursePreviewResponse>> loadCourses(
            @PathVariable final Long studentId
    ) {
        return ResponseEntity.ok(courseQueryService.loadCoursePreviews(studentId));
    }

    @GetMapping(value = "/api/courses/teachers/{teacherId}")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "선생님 아이디로 담당 수업 조회 API", description = "선생님 멤버 ID를 통해 관리중인 반 정보를 조회할 수 있습니다. 선생님, 원장님 권한으로 가능합니다.")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<List<CoursePreviewResponse>> getCourses(
            @PathVariable final Long teacherId
    ) {
        return ResponseEntity.ok(courseQueryService.loadCoursePreviews(teacherId));
    }

    @GetMapping("/api/manage/courses/{courseId}")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반의 세부사항( 메모 X )을 조회하는 기능입니다", description = "선생님이상의 권한을 가져야 합니다")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<CourseDetailResponse> loadCourseDetails(
            @PathVariable final Long courseId
    ) {
        return ResponseEntity.ok(courseQueryService.loadCourseDetails(courseId));
    }
}
