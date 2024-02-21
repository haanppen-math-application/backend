package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.board.config.EntityFieldMappedPageRequest;
import com.hanpyeon.academyapi.board.dto.*;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.service.BoardService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@AllArgsConstructor
public class BoardController {
    BoardService boardService;
    BoardMapper boardMapper;

    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(
            @Nullable @RequestPart("image") List<MultipartFile> multipartFile,
            @Valid @RequestPart("questionRegisterRequestDto") QuestionRegisterRequestDto questionRegisterRequestDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        QuestionRegisterDto dto = boardMapper.createRegisterDto(
                questionRegisterRequestDto,
                multipartFile,
                memberPrincipal.getMemberId()
        );
        boardService.addQuestion(dto);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<QuestionDetails> getSingleQuestionDetails(
            @NotNull @PathVariable Long questionId) {
        return ResponseEntity.ok(boardService.getSingleQuestionDetails(questionId));
    }

    @GetMapping("/question")
    public ResponseEntity<Slice<QuestionPreview>> getQuestionsWithPagination(final EntityFieldMappedPageRequest entityFieldMappedPageRequest) {
        return ResponseEntity.ok(boardService.loadLimitedQuestions(entityFieldMappedPageRequest));
    }

    @PostMapping("/question/{questionId}/comment")
    public ResponseEntity<CommentDetails> addComment(
            @NotNull @PathVariable Long questionId,
            @Valid @RequestPart("commentRegisterRequestDto") CommentRegisterRequestDto commentRegisterRequestDto,
            @RequestPart("imags") List<MultipartFile> images,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        CommentRegisterDto commentRegisterDto = boardMapper.createCommentRegisterDto(questionId, commentRegisterRequestDto, images, memberPrincipal.getMemberId());
        return ResponseEntity.created(null)
                .body(boardService.addComment(commentRegisterDto));
    }
}
