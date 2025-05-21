package com.hpmath.app.api.board.read;

import com.hpmath.app.api.board.read.Responses.QuestionDetailResponse;
import com.hpmath.app.api.board.read.Responses.QuestionPreviewResponse;
import com.hpmath.common.page.PagedResponse;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import com.hpmath.domain.board.read.QusetionQueryOptimizedService;
import com.hpmath.domain.board.read.dto.PagedResult;
import com.hpmath.domain.board.read.dto.QuestionDetailResult;
import com.hpmath.domain.board.read.dto.QuestionPreviewResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardReadController {
    private final QusetionQueryOptimizedService questionQueryService;

    @GetMapping("/api/v2/board/questions/paged/date-desc")
    public ResponseEntity<PagedResponse<QuestionPreviewResponse>> readQuestionsSortedByDate(
            @RequestParam final Integer pageNumber,
            @RequestParam final Integer pageSize
    ) {
        final PagedResult<QuestionPreviewResult> pagedResult = questionQueryService.getPagedResultSortedByDate(pageNumber, pageSize);

        return ResponseEntity.ok(PagedResponse.of(
                pagedResult.datas().stream()
                        .map(QuestionPreviewResponse::from)
                        .toList(),
                pagedResult.totalItemCount(),
                pagedResult.currentPageNumber(),
                pagedResult.pageSize()));
    }

    @GetMapping("/api/v2/board/questions")
    @Authorization(opened = true)
    public ResponseEntity<QuestionDetailResponse> readQuestions(
            @RequestParam final Long questionId,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final QuestionDetailResult result = questionQueryService.getDetail(questionId, memberPrincipal.memberId());

        return ResponseEntity.ok().body(QuestionDetailResponse.from(result));
    }
}
