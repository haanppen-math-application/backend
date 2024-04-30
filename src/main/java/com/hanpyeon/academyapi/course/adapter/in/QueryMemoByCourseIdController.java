package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.course.application.dto.MemoQueryCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoView;
import com.hanpyeon.academyapi.course.application.port.in.LoadMemoQuery;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
class QueryMemoByCourseIdController {

    private final LoadMemoQuery loadMemoQuery;

    @GetMapping("/api/courses/{courseId}/memos")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반의 메모들을 조회하기 위한 API", description = "해당 API는 로그인된 사용자면 모두 가능합니다. \n" +
            "(1) 페이지 번호를 반드시 명시해 주세요.(default = 0)\n" +
            "(2) 한번의 요청당 5개의 엘리먼트가 들어옵니다.\n" +
            "(3) 페이지를 하나씩 늘려가며 요청할것\n" +
            "(4) 존재하지 않는 반에 대한 요청은 에러처리")
    public ResponseEntity<Slice<MemoView>> loadMemos(
            @PageableDefault(size = 5, page = 0, direction = Sort.Direction.ASC) Pageable pageable,
            @Nonnull @PathVariable Long courseId,
            @AuthenticationPrincipal @Nonnull MemberPrincipal memberPrincipal
            ) {
        final MemoQueryCommand command = new MemoQueryCommand(memberPrincipal.memberId(), pageable, courseId);
        return ResponseEntity.ok(loadMemoQuery.loadMemos(command));
    }
}
