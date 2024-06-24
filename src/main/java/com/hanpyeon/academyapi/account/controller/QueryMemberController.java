package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.PreviewStudent;
import com.hanpyeon.academyapi.account.dto.PreviewTeacher;
import com.hanpyeon.academyapi.account.service.QueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class QueryMemberController {
    private final QueryService queryService;

    @GetMapping("/teachers")
    @Operation(summary = "전체 선생 조회 API", description = "인증된 사용자면 모두 가능, ?name={찾고자 하는 이름} 시 검색, name이 없을시, 전체 조회")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<List<PreviewTeacher>> queryTeachers(@RequestParam(defaultValue = "null") String name) {
        List<PreviewTeacher> teachers = queryService.loadTeachers(name);
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/students")
    @Operation(summary = "전체 학생 조회 API", description = "인증된 사용자면 모두 가능")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<List<PreviewStudent>> queryStudents(@RequestParam(defaultValue = "null") String name) {
        List<PreviewStudent> students = queryService.loadStudents(name);
        return ResponseEntity.ok(students);
    }
}
