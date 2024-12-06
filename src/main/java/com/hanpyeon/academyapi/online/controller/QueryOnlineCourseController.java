package com.hanpyeon.academyapi.online.controller;

import com.hanpyeon.academyapi.online.dto.OnlineCoursePreview;
import com.hanpyeon.academyapi.online.dto.QueryMyOnlineCourseCommand;
import com.hanpyeon.academyapi.online.dto.QueryOnlineCourseByStudentIdCommand;
import com.hanpyeon.academyapi.online.dto.QueryOnlineCourseByTeacherIdCommand;
import com.hanpyeon.academyapi.online.service.QueryOnlineCourseService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/online-courses")
@RequiredArgsConstructor
@Tag(name = "ONLINE COURSE")
public class QueryOnlineCourseController {
    private final QueryOnlineCourseService queryOnlineCourseService;

    @GetMapping
    public ResponseEntity<List<OnlineCoursePreview>> queryAllCourses() {
        return ResponseEntity.ok(queryOnlineCourseService.queryAll());
    }

    @GetMapping("/teachers/{teacherId}")
    public ResponseEntity<List<OnlineCoursePreview>> queryOnlineCoursesByTeacherId(
            @PathVariable final Long teacherId
    ) {
        final QueryOnlineCourseByTeacherIdCommand queryOnlineCourseByTeacherIdCommand =
                new QueryOnlineCourseByTeacherIdCommand(teacherId);
        return ResponseEntity.ok(
                queryOnlineCourseService.queryOnlineCourseByTeacherId(queryOnlineCourseByTeacherIdCommand));
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<OnlineCoursePreview>> queryOnlineCoursesByStudentId(
            @PathVariable final Long studentId
    ) {
        final QueryOnlineCourseByStudentIdCommand queryOnlineCourseByStudentIdCommand =
                new QueryOnlineCourseByStudentIdCommand(studentId);

        return ResponseEntity.ok(queryOnlineCourseService.queryOnlineCourseByStudentId(queryOnlineCourseByStudentIdCommand));
    }

    @GetMapping("/my")
    public ResponseEntity<List<OnlineCoursePreview>> queryOnlineCoursesByStudentId(
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final QueryMyOnlineCourseCommand queryMyOnlineCourseCommand = new QueryMyOnlineCourseCommand(
                memberPrincipal.memberId(), memberPrincipal.role());
        return ResponseEntity.ok(
                queryOnlineCourseService.queryMyOnlineCourses(queryMyOnlineCourseCommand));
    }
}
