package com.hpmath.app.api.course.controller;

import static com.hpmath.app.api.course.controller.Requests.QueryMemoByCourseIdAndDateRequest;

import com.hpmath.common.page.PagedResponse;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import com.hpmath.domain.course.service.CourseMemoQueryService;
import com.hpmath.domain.course.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.dto.MemoQueryCommand;
import com.hpmath.domain.course.dto.Responses.MemoAppliedDayResponse;
import com.hpmath.domain.course.dto.Responses.MemoViewResponse;
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
    private final CourseMemoQueryService courseMemoQueryService;

    @GetMapping(value = "/api/courses/memos")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "날짜와 courseId로 메모 조히 API", description = "두개 이상 조회될 시 에러 MEMO_DUPLICATED_EXCEPTION 발생")
    @Authorization(opened = true)
    public ResponseEntity<MemoViewResponse> loadMemo(
            @ModelAttribute @Valid final QueryMemoByCourseIdAndDateRequest queryMemoByCourseIdAndDateRequest
    ) {
        final MemoQueryByCourseIdAndDateCommand command = queryMemoByCourseIdAndDateRequest.toCommand();
        final MemoViewResponse memoView = courseMemoQueryService.loadSingleMemo(command);
        if (Objects.isNull(memoView)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courseMemoQueryService.loadSingleMemo(command));
    }

    @GetMapping("/api/courses/{courseId}/memos")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반의 메모들을 조회하기 위한 API", description = "해당 API는 로그인된 사용자면 모두 가능합니다. \n" +
            "(1) 페이지 번호를 반드시 명시해 주세요.(default = 0)\n" +
            "(2) 한번의 요청당 5개의 엘리먼트가 들어옵니다.\n" +
            "(3) 페이지를 하나씩 늘려가며 요청할것\n" +
            "(4) 존재하지 않는 반에 대한 요청은 에러처리 됩니다")
    @Authorization(opened = true)
    public ResponseEntity<PagedResponse<MemoViewResponse>> loadMemos(
            @PageableDefault(size = 10, direction = Sort.Direction.DESC, sort = "targetDate") Pageable pageable,
            @Nonnull @PathVariable Long courseId
    ) {
        final MemoQueryCommand command = new MemoQueryCommand(pageable, courseId);
        final Page<MemoViewResponse> memoViews = courseMemoQueryService.loadMemos(command);
        return ResponseEntity.ok(
                PagedResponse.of(
                        memoViews.getContent(),
                        memoViews.getTotalElements(),
                        memoViews.getNumber(),
                        memoViews.getSize()));
    }

    @GetMapping("/api/courses/memos/month")
    @Operation(summary = "로그인된 학생 ID와 날짜정보를 활용하여, 해당 달의 학생 수업 정보를 가져오는 API")
    @Authorization(opened = true)
    public ResponseEntity<List<MemoAppliedDayResponse>> queryByMonthInfo(
            @RequestParam(required = true) final LocalDate monthInfo,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        return ResponseEntity.ok(courseMemoQueryService.query(monthInfo, memberPrincipal.memberId()));
    }
}
