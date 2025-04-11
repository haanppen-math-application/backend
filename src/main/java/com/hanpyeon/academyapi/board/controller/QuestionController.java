package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.board.config.EntityFieldMappedPageRequest;
import com.hanpyeon.academyapi.board.controller.Requests.QuestionDeleteRequest;
import com.hanpyeon.academyapi.board.controller.Requests.QuestionRegisterRequest;
import com.hanpyeon.academyapi.board.controller.Requests.QuestionUpdateRequest;
import com.hanpyeon.academyapi.board.controller.Responses.QuestionDetails;
import com.hanpyeon.academyapi.board.controller.Responses.QuestionPreview;
import com.hanpyeon.academyapi.board.dto.QuestionDeleteCommand;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterCommand;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateCommand;
import com.hanpyeon.academyapi.board.service.question.QuestionService;
import com.hanpyeon.academyapi.paging.PagedResponse;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/board/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @Operation(summary = "질문 등록 API", description = "질문을 등록하는 API 입니다")
    @SecurityRequirement(name = "jwtAuth")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addQuestion(
            @Valid @RequestBody final QuestionRegisterRequest questionRegisterRequestDto,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final QuestionRegisterCommand questionRegisterCommand = questionRegisterRequestDto.toCommand(memberPrincipal.memberId());
        final Long createdQuestionId = questionService.addQuestion(questionRegisterCommand);

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
            @NotNull @PathVariable final Long questionId
    ) {
        return ResponseEntity.ok(questionService.getSingleQuestionDetails(questionId));
    }

    @Operation(summary = "질문 게시판 조회", description = "전체 질문을 페이지별로 조회하는 기능입니다. sort 또한 쿼리 파라미터로 보내면 되며, sort=date 로 날짜순, sort=solve로 풀어진 문제 순 으로 정렬할 수 있습니다\n" +
            "?cursorIndex= 를 통해 해당 ID 이후의 질문들을 받을 수 있습니다.\n" +
            "?size= 를 통해 한번에 받아올 게시물의 수를 조절\n" +
            "?sort=date 로 날짜 오름차순 \n")
    @GetMapping
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<PagedResponse<QuestionPreview>> getQuestionsWithPagination(
            @ParameterObject @Parameter(description = "date : 날짜 순, solve : 풀어진 문제 순", example = "date") final EntityFieldMappedPageRequest entityFieldMappedPageRequest,
            @RequestParam(required = false) final String title
    ) {
        return ResponseEntity.ok(questionService.loadQuestionsByOffset(
                entityFieldMappedPageRequest,
                title)
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
        return ResponseEntity.ok(questionService.loadMyQuestionsByOffset(
                memberPrincipal.memberId(),
                entityFieldMappedPageRequest,
                title)
        );
    }

    @Operation(summary = "질문 수정 API", description = "질문 수정은 본인만 가능합니다")
    @SecurityRequirement(name = "jwtAuth")
    @PutMapping
    public ResponseEntity<?> updateQuestion(
            @Valid @RequestBody final QuestionUpdateRequest questionUpdateRequestDto,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final QuestionUpdateCommand questionUpdateCommand = questionUpdateRequestDto.toCommand(
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        return ResponseEntity.ok(questionService.updateQuestion(questionUpdateCommand));
    }

    @Operation(summary = "질문 삭제 API", description = "작성된 질문은 선생님, 매니저 권한만 가능합니다")
    @SecurityRequirement(name = "jwtAuth")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(
            @ModelAttribute @Valid final QuestionDeleteRequest questionDeleteRequestDto,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final QuestionDeleteCommand questionDeleteDto = questionDeleteRequestDto.toCommand(
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        questionService.deleteQuestion(questionDeleteDto);
        return ResponseEntity.noContent().build();
    }
}
