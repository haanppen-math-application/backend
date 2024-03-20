package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.PreviewStudent;
import com.hanpyeon.academyapi.account.dto.PreviewTeacher;
import com.hanpyeon.academyapi.account.service.QueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class QueryMemberController {
    private final QueryService queryService;

    @GetMapping("/teachers")
    @Operation(summary = "전체 선생 조회 API", description = "선생님 이상의 권한이 필요")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<List<PreviewTeacher>> queryTeachers() {
        List<PreviewTeacher> teachers = queryService.loadTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/students")
    @Operation(summary = "전체 학생 조회 API", description = "선생님 이상의 권한이 필요")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<List<PreviewStudent>> queryStudents() {
        List<PreviewStudent> students = queryService.loadStudents();
        return ResponseEntity.ok(students);
    }

}
