package com.hanpyeon.academyapi.course.controller;

import static com.hanpyeon.academyapi.course.controller.Requests.QueryMemoByCourseIdAndDateRequest;

import com.hanpyeon.academyapi.course.controller.Responses.MemoAppliedDayResponse;
import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoQueryCommand;
import com.hanpyeon.academyapi.course.controller.Responses.MemoViewResponse;
import com.hanpyeon.academyapi.course.application.port.in.LoadMemoQuery;
import com.hanpyeon.academyapi.course.application.port.in.QueryCourseByMonthUseCase;
import com.hanpyeon.academyapi.course.application.port.in.QueryMemoByCourseIdAndDateUseCase;
import com.hanpyeon.academyapi.paging.PagedResponse;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemoQueryController {
    private final QueryMemoByCourseIdAndDateUseCase queryMemoByCourseIdAndDateUseCase;
    private final QueryCourseByMonthUseCase queryCourseByMonthUseCase;
    private final LoadMemoQuery loadMemoQuery;

    @GetMapping(value = "/api/courses/memos")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "날짜와 courseId로 메모 조히 API", description = "두개 이상 조회될 시 에러 MEMO_DUPLICATED_EXCEPTION 발생")
    public ResponseEntity<MemoViewResponse> loadMemo(
            @ModelAttribute @Valid final QueryMemoByCourseIdAndDateRequest queryMemoByCourseIdAndDateRequest
    ) {
        final MemoQueryByCourseIdAndDateCommand command = queryMemoByCourseIdAndDateRequest.toCommand();
        final MemoViewResponse memoView = queryMemoByCourseIdAndDateUseCase.loadSingleMemo(command);
        if (Objects.isNull(memoView)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(queryMemoByCourseIdAndDateUseCase.loadSingleMemo(command));
    }

    @GetMapping("/api/courses/{courseId}/memos")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반의 메모들을 조회하기 위한 API", description = "해당 API는 로그인된 사용자면 모두 가능합니다. \n" +
            "(1) 페이지 번호를 반드시 명시해 주세요.(default = 0)\n" +
            "(2) 한번의 요청당 5개의 엘리먼트가 들어옵니다.\n" +
            "(3) 페이지를 하나씩 늘려가며 요청할것\n" +
            "(4) 존재하지 않는 반에 대한 요청은 에러처리 됩니다")
    public ResponseEntity<PagedResponse<MemoViewResponse>> loadMemos(
            @PageableDefault(size = 10, direction = Sort.Direction.DESC, sort = "targetDate") Pageable pageable,
            @Nonnull @PathVariable Long courseId
    ) {
        final MemoQueryCommand command = new MemoQueryCommand(pageable, courseId);
        final Page<MemoViewResponse> memoViews = loadMemoQuery.loadMemos(command);
        return ResponseEntity.ok(PagedResponse.of(memoViews));
    }

    @GetMapping("/api/courses/memos/month")
    @Operation(summary = "로그인된 학생 ID와 날짜정보를 활용하여, 해당 달의 학생 수업 정보를 가져오는 API")
    public ResponseEntity<List<MemoAppliedDayResponse>> queryByMonthInfo(
            @RequestParam(required = true) final LocalDate monthInfo,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        return ResponseEntity.ok(queryCourseByMonthUseCase.query(monthInfo, memberPrincipal.memberId()));
    }
}
