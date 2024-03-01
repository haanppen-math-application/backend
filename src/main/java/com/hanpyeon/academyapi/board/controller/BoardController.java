package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.board.config.EntityFieldMappedPageRequest;
import com.hanpyeon.academyapi.board.dto.*;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.service.comment.CommentService;
import com.hanpyeon.academyapi.board.service.question.QuestionService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@AllArgsConstructor
public class BoardController {
    private final QuestionService questionService;
    private final CommentService commentService;
    private final BoardMapper boardMapper;

    @Operation(summary = "질문 등록 API", description = "질문을 등록하는 API 입니다")
    @SecurityRequirement(name = "jwtAuth")
    @PostMapping(value = "/question", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    @GetMapping("/question/{questionId}")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<QuestionDetails> getSingleQuestionDetails(
            @NotNull @PathVariable Long questionId) {
        return ResponseEntity.ok(questionService.getSingleQuestionDetails(questionId));
    }

    @GetMapping("/question")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<Slice<QuestionPreview>> getQuestionsWithPagination(final EntityFieldMappedPageRequest entityFieldMappedPageRequest) {
        return ResponseEntity.ok(questionService.loadLimitedQuestions(entityFieldMappedPageRequest));
    }

    @Operation(summary = "댓글 등록 API", description = "질문 게시글에 댓글을 달 수 있도록 하는 API 입니다.")
    @PostMapping(value = "/question/comment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> addComment(
            @Valid @ModelAttribute CommentRegisterRequestDto commentRegisterRequestDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        CommentRegisterDto commentRegisterDto = boardMapper.createCommentRegisterDto(commentRegisterRequestDto, memberPrincipal.memberId());
        final Long createdCommentId = commentService.addComment(commentRegisterDto);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdCommentId)
                        .toUri()
        ).build();
    }

    @PatchMapping(value = "/questions/comments/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> updateComment(
            @PathVariable final Long commentId,
            @RequestBody final CommentUpdateRequestDto commentAdoptRequestDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        final CommentUpdateDto commentUpdateDto = boardMapper.createCommentUpdateDto(commentAdoptRequestDto, commentId, memberPrincipal.memberId());
        commentService.updateComment(commentUpdateDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/questions/comments/{commentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> updateCommentWithImages(
            @PathVariable final Long commentId,
            @Nullable @RequestPart("images") final List<MultipartFile> images,
            @Nullable @RequestPart("data") final CommentUpdateRequestDto commentUpdateRequestDto,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal) {
        final CommentUpdateDto commentUpdateDto = boardMapper.createCommentUpdateDto(commentUpdateRequestDto, commentId, memberPrincipal.memberId(), images);
        commentService.updateComment(commentUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/question/comments/{commentId}")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> deleteComment(
            @NotNull @PathVariable final Long commentId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal) {
        CommentDeleteDto commentDeleteDto = boardMapper.createCommentDeleteDto(commentId, memberPrincipal.memberId());
        commentService.deleteComment(commentDeleteDto);
        return ResponseEntity.noContent().build();
    }
}
