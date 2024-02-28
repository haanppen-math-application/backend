package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.board.config.EntityFieldMappedPageRequest;
import com.hanpyeon.academyapi.board.dto.*;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.service.comment.CommentService;
import com.hanpyeon.academyapi.board.service.question.QuestionService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
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

    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(
            @Nullable @RequestPart("images") List<MultipartFile> multipartFile,
            @Valid @RequestPart("questionRegisterRequestDto") QuestionRegisterRequestDto questionRegisterRequestDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        QuestionRegisterDto questionRegisterDto = boardMapper.createRegisterDto(questionRegisterRequestDto, multipartFile, memberPrincipal.memberId());
        final Long createdQuestionId = questionService.addQuestion(questionRegisterDto);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdQuestionId)
                .toUri()
        ).build();
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<QuestionDetails> getSingleQuestionDetails(
            @NotNull @PathVariable Long questionId) {
        return ResponseEntity.ok(questionService.getSingleQuestionDetails(questionId));
    }

    @GetMapping("/question")
    public ResponseEntity<Slice<QuestionPreview>> getQuestionsWithPagination(final EntityFieldMappedPageRequest entityFieldMappedPageRequest) {
        return ResponseEntity.ok(questionService.loadLimitedQuestions(entityFieldMappedPageRequest));
    }

    @PostMapping(value = "/question/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComment(
            @Valid @RequestPart("commentRegisterRequestDto") CommentRegisterRequestDto commentRegisterRequestDto,
            @Nullable @RequestPart("images") List<MultipartFile> images,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        CommentRegisterDto commentRegisterDto = boardMapper.createCommentRegisterDto(commentRegisterRequestDto, images, memberPrincipal.memberId());
        final Long createdCommentId = commentService.addComment(commentRegisterDto);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCommentId)
                .toUri()
        ).build();
    }

    @PatchMapping(value = "/questions/comments/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateComment(
            @PathVariable final Long commentId,
            @RequestBody final CommentUpdateRequestDto commentAdoptRequestDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        final CommentUpdateDto commentUpdateDto = boardMapper.createCommentUpdateDto(commentAdoptRequestDto, commentId, memberPrincipal.memberId());
        commentService.updateComment(commentUpdateDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/questions/comments/{commentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<?> deleteComment(
            @NotNull @PathVariable final Long commentId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal) {
        CommentDeleteDto commentDeleteDto = boardMapper.createCommentDeleteDto(commentId, memberPrincipal.memberId());
        commentService.deleteComment(commentDeleteDto);
        return ResponseEntity.noContent().build();
    }

}
