package com.hanpyeon.academyapi.board.controller;

import com.hanpyeon.academyapi.board.QuestionService;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterRequestDto;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    @PostMapping
    public ResponseEntity<?> addQuestion(
            @Nullable @RequestPart("image") List<MultipartFile> multipartFile,
            @Valid @RequestPart("questionRegisterRequestDto") QuestionRegisterRequestDto questionRegisterRequestDto) {

        return ResponseEntity.created(null).build();
    }
}
