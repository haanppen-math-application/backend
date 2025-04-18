package com.hpmath.academyapi.board.controller;

import com.hpmath.academyapi.board.config.EntityFieldMappedPageRequest;
import com.hpmath.academyapi.board.controller.Responses.QuestionDetails;
import com.hpmath.academyapi.board.controller.Responses.QuestionPreview;
import com.hpmath.academyapi.board.service.question.QuestionQueryService;
import com.hpmath.academyapi.paging.PagedResponse;
import com.hpmath.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board/questions")
@RequiredArgsConstructor
@Tag(name = "Questions")
public class QuestionQueryController {
    private final QuestionQueryService questionQueryService;

    @Operation(summary = "질문 상세보기 API", description = "질문의 등록 날짜, 댓글 내용, 조회 수 등 상세 정보를 알 수 있는 API 입니다.")
    @GetMapping("/{questionId}")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<QuestionDetails> getSingleQuestionDetails(
            @PathVariable final Long questionId
    ) {
        return ResponseEntity.ok(questionQueryService.getSingleQuestionDetails(questionId));
    }

    @Operation(summary = "질문 게시판 조회", description =
            "전체 질문을 페이지별로 조회하는 기능입니다. sort 또한 쿼리 파라미터로 보내면 되며, sort=date 로 날짜순, sort=solve로 풀어진 문제 순 으로 정렬할 수 있습니다\n" +
                    "?cursorIndex= 를 통해 해당 ID 이후의 질문들을 받을 수 있습니다.\n" +
                    "?size= 를 통해 한번에 받아올 게시물의 수를 조절\n" +
                    "?sort=date 로 날짜 오름차순 \n")
    @GetMapping
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<PagedResponse<QuestionPreview>> getQuestionsWithPagination(
            @ParameterObject @Parameter(description = "date : 날짜 순, solve : 풀어진 문제 순", example = "date") final EntityFieldMappedPageRequest entityFieldMappedPageRequest,
            @RequestParam(required = false) final String title
    ) {
        return ResponseEntity.ok(questionQueryService.loadQuestionsByOffset(
                        entityFieldMappedPageRequest,
                        title
                )
        );
    }

    @Operation(summary = "나의 질문 조회")
    @GetMapping("/my")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<PagedResponse<QuestionPreview>> getMyQuestions(
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal,
            @ParameterObject final EntityFieldMappedPageRequest entityFieldMappedPageRequest,
            @RequestParam(required = false) final String title
    ) {
        return ResponseEntity.ok(questionQueryService.loadMyQuestionsByOffset(
                memberPrincipal.memberId(),
                entityFieldMappedPageRequest,
                title)
        );
    }
}
