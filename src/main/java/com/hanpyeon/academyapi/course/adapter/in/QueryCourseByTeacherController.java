package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.application.port.in.LoadCoursesByTeacherQuery;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class QueryCourseByTeacherController {
    private final LoadCoursesByTeacherQuery loadCoursesQuery;
    @GetMapping(value = "/api/courses/my")
    public ResponseEntity<List<CoursePreview>> queryMyCourses(
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
            ) {
        return ResponseEntity.ok(loadCoursesQuery.loadCoursePreviews(memberPrincipal.memberId()));
    }
}
