package com.hpmath.app.memberapi.api;

import com.hpmath.app.memberapi.api.Responses.PreviewStudentResponse;
import com.hpmath.app.memberapi.api.Responses.PreviewTeacherResponse;
import com.hpmath.domain.member.dto.MemberInfoResult;
import com.hpmath.domain.member.dto.StudentPageQuery;
import com.hpmath.domain.member.dto.StudentQuery;
import com.hpmath.domain.member.dto.TeacherPageQuery;
import com.hpmath.domain.member.dto.TeacherQuery;
import com.hpmath.domain.member.service.AccountQueryService;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.paging.CursorResponse;
import com.hpmath.common.web.paging.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class AccountQueryController {
    private final AccountQueryService queryService;

    @GetMapping("/teachers")
    @Operation(summary = "커서기반 선생 조회 API", description = "인증된 사용자면 모두 가능, \n" +
            "?cursorIndex={다음 커서}. 없을 시 초반부 검색\n" +
            "?size={페이지 크기 지정}. 기본 값 5 로 설정 \n" +
            "?name={찾고자 하는 이름} 시 검색, name이 없을시, 전체 조회\n")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(opened = true)
    public ResponseEntity<CursorResponse<PreviewTeacherResponse>> queryTeachers(
            @RequestParam(required = false, defaultValue = "0") Long cursorIndex,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false) String name
    ) {
        final TeacherQuery teacherQuery = new TeacherQuery(cursorIndex, size, name);
        CursorResponse<MemberInfoResult> teachers = queryService.loadTeachers(teacherQuery);

        CursorResponse<PreviewTeacherResponse> response = new CursorResponse<>(
                teachers.contents().stream()
                        .map(PreviewTeacherResponse::of)
                        .toList(),
                teachers.nextCursor()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/teachers/paging")
    @Operation(summary = "페이징 선생 조회 API", description = "인증 필요, 이름 기준 오름차순 조회")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(opened = true)
    public ResponseEntity<PagedResponse<PreviewTeacherResponse>> queryTeachers(
            @PageableDefault(size = 5) final Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        final TeacherPageQuery teacherPageQuery = new TeacherPageQuery(name, pageable);
        Page<MemberInfoResult> previewTeachersResult = queryService.loadTeachers(teacherPageQuery);

        PagedResponse<PreviewTeacherResponse> previewTeachers = PagedResponse.of(
                previewTeachersResult.getContent().stream()
                        .map(PreviewTeacherResponse::of)
                        .toList(),
                previewTeachersResult.getTotalElements(),
                previewTeachersResult.getNumber(),
                previewTeachersResult.getSize()
        );
        return ResponseEntity.ok(previewTeachers);
    }

    @GetMapping("/students/paging")
    @Operation(summary = "페이징 학생 조회 API", description = "인증 필요, 이름 기준 오름차순 조회")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(opened = true)
    public ResponseEntity<PagedResponse<PreviewStudentResponse>> queryStudents(
            @PageableDefault(size = 5) final Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") Integer startGrade,
            @RequestParam(required = false, defaultValue = "11") Integer endGrade
    ) {
        final StudentPageQuery studentPageQuery = new StudentPageQuery(name, startGrade, endGrade, pageable);
        Page<MemberInfoResult> previewStudentsResults = queryService.loadStudents(studentPageQuery);
        PagedResponse<PreviewStudentResponse> previewStudents = PagedResponse.of(
                previewStudentsResults.getContent()
                        .stream()
                        .map(PreviewStudentResponse::of)
                        .toList(),
                previewStudentsResults.getTotalElements(),
                previewStudentsResults.getNumber(),
                previewStudentsResults.getSize()
        );
        return ResponseEntity.ok(previewStudents);
    }

    @GetMapping("/students")
    @Operation(summary = "커서 기반 학생 조회 API", description = "인증된 사용자면 모두 가능\n" +
            "?cursorIndex={다음 커서}. 없을 시 초반부 검색\n" +
            "?size={페이지 크기 지정}. 기본 값 5 로 설정\n" +
            "?name : 이름을 이용한 검색 (없을 시, 전체 검색)\n" +
            "?startRange : 기본값 0, include\n" +
            "?endRange : 기본값 11, include")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(opened = true)
    public ResponseEntity<CursorResponse<PreviewStudentResponse>> queryStudents(
            @RequestParam(required = false, defaultValue = "0") Long cursorIndex,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") Integer startGrade,
            @RequestParam(required = false, defaultValue = "11") Integer endGrade
    ) {
        final StudentQuery studentQuery = new StudentQuery(cursorIndex, size, name, startGrade, endGrade);
        CursorResponse<MemberInfoResult> studentsResults = queryService.loadStudents(studentQuery);

        final CursorResponse<PreviewStudentResponse> students = new CursorResponse<>(studentsResults.contents().stream()
                .map(PreviewStudentResponse::of)
                .toList(), studentsResults.nextCursor());
        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/all")
    @Operation(summary = "전체 학생 조회 API")
    @Authorization(opened = true)
    public ResponseEntity<List<PreviewStudentResponse>> getAllStudents() {
        final List<PreviewStudentResponse> students = queryService.loadAllStudents().stream()
                .map(PreviewStudentResponse::of)
                .toList();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/teachers/all")
    @Operation(summary = "전체 선생 조회 API")
    @Authorization(opened = true)
    public ResponseEntity<List<PreviewTeacherResponse>> getAllTeachers() {
        final List<PreviewTeacherResponse> teachers = queryService.loadALlTeachers().stream()
                .map(PreviewTeacherResponse::of)
                .toList();
        return ResponseEntity.ok(teachers);
    }
}
