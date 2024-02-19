package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.board.dto.QuestionDetails;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterDto;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterRequestDto;
import com.hanpyeon.academyapi.board.mapper.QuestionMapper;
import com.hanpyeon.academyapi.board.service.BoardService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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
    QuestionMapper questionMapper;

    @PostMapping
    public ResponseEntity<?> addQuestion(
            @Nullable @RequestPart("image") List<MultipartFile> multipartFile,
            @Valid @RequestPart("questionRegisterRequestDto") QuestionRegisterRequestDto questionRegisterRequestDto,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        QuestionRegisterDto dto = questionMapper.createRegisterDto(
                questionRegisterRequestDto,
                multipartFile,
                memberPrincipal.getMemberId()
        );
        boardService.addQuestion(dto);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDetails> getSingleQuestionDetails(
            @NotNull @PathVariable Long questionId) {
        return ResponseEntity.ok(boardService.getSingleQuestionDetails(questionId));
    }
}
