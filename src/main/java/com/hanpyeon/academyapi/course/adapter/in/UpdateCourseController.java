package com.hanpyeon.academyapi.course.adapter.in;


import com.hanpyeon.academyapi.course.application.dto.CourseUpdateDto;
import com.hanpyeon.academyapi.course.application.port.in.UpdateCourseUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
class UpdateCourseController {

    private final UpdateCourseUseCase updateCourseNameUseCase;

    @Operation(summary = "개발중")
    @PutMapping(value = "/{courseId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCourseName(
            final @PathVariable Long courseId,
            final @RequestBody CourseUpdateRequest courseUpdateRequest,
            final @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final CourseUpdateDto courseUpdateDto = courseUpdateRequest.mapToCourseUpdateDto(courseId, memberPrincipal.memberId());
        updateCourseNameUseCase.updateCourse(courseUpdateDto);

        return ResponseEntity.ok().build();
    }

    static record CourseUpdateRequest(
            String courseName,
            Long newTeacherId
    ) {
        private CourseUpdateDto mapToCourseUpdateDto(final Long courseId, final Long requestMemberId) {
            return new CourseUpdateDto(
                    courseId,
                    requestMemberId,
                    courseName,
                    newTeacherId
            );
        }
    }
}
