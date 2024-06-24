package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.PreviewStudent;
import com.hanpyeon.academyapi.account.dto.PreviewTeacher;
import com.hanpyeon.academyapi.account.dto.StudentQueryDto;
import com.hanpyeon.academyapi.account.service.QueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class QueryMemberController {
    private final QueryService queryService;

    @GetMapping("/teachers")
    @Operation(summary = "전체 선생 조회 API", description = "인증된 사용자면 모두 가능, ?name={찾고자 하는 이름} 시 검색, name이 없을시, 전체 조회")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<List<PreviewTeacher>> queryTeachers(@RequestParam(required = false) String name) {
        List<PreviewTeacher> teachers = queryService.loadTeachers(name);
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/students")
    @Operation(summary = "전체 학생 조회 API", description = "인증된 사용자면 모두 가능\n" +
            "name : 이름을 이용한 검색 (없을 시, 전체 검색)\n" +
            "startRange : 기본값 0, include" +
            "endRange : 기본값 11, include")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<List<PreviewStudent>> queryStudents(@RequestParam(required = false) String name,
                                                              @RequestParam(required = false, defaultValue = "0") Integer startGrade,
                                                              @RequestParam(required = false, defaultValue = "11") Integer endGrade
    ) {
        final StudentQueryDto queryDto = new StudentQueryDto(name, startGrade, endGrade);
        List<PreviewStudent> students = queryService.loadStudents(queryDto);
        return ResponseEntity.ok(students);
    }
}
