package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.board.config.EntityFieldMappedPageRequest;
import com.hanpyeon.academyapi.board.dto.*;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.service.question.QuestionService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@AllArgsConstructor
@RestController
@RequestMapping("/api/board/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final BoardMapper boardMapper;

    @Operation(summary = "질문 등록 API", description = "질문을 등록하는 API 입니다")
    @SecurityRequirement(name = "jwtAuth")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addQuestion(
            @Valid @ModelAttribute QuestionRegisterRequestDto questionRegisterRequestDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        QuestionRegisterDto questionRegisterDto = boardMapper.createRegisterDto(questionRegisterRequestDto, memberPrincipal.memberId());
        final Long createdQuestionId = questionService.addQuestion(questionRegisterDto);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdQuestionId)
                .toUri()
        ).build();
    }

    @Operation(summary = "질문 상세보기 API", description = "질문의 등록 날짜, 댓글 내용, 조회 수 등 상세 정보를 알 수 있는 API 입니다.")
    @GetMapping("/{questionId}")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<QuestionDetails> getSingleQuestionDetails(
            @NotNull @PathVariable Long questionId) {
        return ResponseEntity.ok(questionService.getSingleQuestionDetails(questionId));
    }

    @Operation(summary = "질문 게시판 조회", description = "전체 질문을 페이지별로 조회하는 기능입니다. sort 또한 쿼리 파라미터로 보내면 되며, sort=date 로 날짜순, sort=solve로 풀어진 문제 순 으로 정렬할 수 있습니다.")
    @GetMapping
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<Slice<QuestionPreview>> getQuestionsWithPagination(
            @ParameterObject @Parameter(description = "date : 날짜 순, solve : 풀어진 문제 순", example = "date") final EntityFieldMappedPageRequest entityFieldMappedPageRequest) {
        return ResponseEntity.ok(questionService.loadLimitedQuestions(entityFieldMappedPageRequest));
    }

    @PatchMapping
    public ResponseEntity<?> updateQuestion(
            @Valid @ModelAttribute QuestionUpdateRequestDto questionUpdateRequestDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final QuestionUpdateDto questionUpdateDto = boardMapper.createQuestionUpdateDto(questionUpdateRequestDto, memberPrincipal.memberId());
        return ResponseEntity.ok(questionService.updateQuestion(questionUpdateDto));
    }
}
