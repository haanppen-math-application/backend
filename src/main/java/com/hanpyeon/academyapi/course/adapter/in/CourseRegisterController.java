package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.CourseRegisterDto;
import com.hanpyeon.academyapi.course.application.port.in.CourseRegisterUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
class CourseRegisterController {
    private final CourseRegisterUseCase courseRegisterUseCase;

    @PostMapping
    public ResponseEntity<?> registerCourse(
            @Valid @RequestBody final CourseRegisterRequestDto courseRegisterRequestDto,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final CourseRegisterDto courseRegisterDto = CourseRegisterRequestDto.mapToRegisterDto(courseRegisterRequestDto, memberPrincipal.memberId());
        final Long createdCourseId = courseRegisterUseCase.register(courseRegisterDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCourseId)
                .toUri()
        ).build();
    }

    static record CourseRegisterRequestDto(
            @NotNull String courseName,
            @NotNull Long teacherId,
            List<Long> students
    ) {
        private static CourseRegisterDto mapToRegisterDto(final CourseRegisterRequestDto courseRegisterRequestDto, final Long requestMemberId) {
            return new CourseRegisterDto(
                    courseRegisterRequestDto.courseName(),
                    courseRegisterRequestDto.teacherId(),
                    courseRegisterRequestDto.students(),
                    requestMemberId
            );
        }
    }
}
