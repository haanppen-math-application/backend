package com.hpmath.academyapi.board.controller;

import com.hpmath.academyapi.board.controller.Requests.QuestionDeleteRequest;
import com.hpmath.academyapi.board.controller.Requests.QuestionRegisterRequest;
import com.hpmath.academyapi.board.controller.Requests.QuestionUpdateRequest;
import com.hpmath.academyapi.board.dto.QuestionDeleteCommand;
import com.hpmath.academyapi.board.dto.QuestionRegisterCommand;
import com.hpmath.academyapi.board.dto.QuestionUpdateCommand;
import com.hpmath.academyapi.board.service.question.QuestionDeleteService;
import com.hpmath.academyapi.board.service.question.QuestionRegisterService;
import com.hpmath.academyapi.board.service.question.QuestionUpdateService;
import com.hpmath.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/board/questions")
@RequiredArgsConstructor
@Tag(name = "Questions")
public class QuestionController {
    private final QuestionUpdateService questionUpdateService;
    private final QuestionRegisterService questionRegisterService;
    private final QuestionDeleteService questionDeleteService;

    @Operation(summary = "질문 등록 API", description = "질문을 등록하는 API 입니다")
    @SecurityRequirement(name = "jwtAuth")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addQuestion(
            @Valid @RequestBody final QuestionRegisterRequest questionRegisterRequestDto,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final QuestionRegisterCommand questionRegisterCommand = questionRegisterRequestDto.toCommand(memberPrincipal.memberId());
        final Long createdQuestionId = questionRegisterService.addQuestion(questionRegisterCommand);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdQuestionId)
                .toUri()
        ).build();
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
        return ResponseEntity.ok(questionUpdateService.updateQuestion(questionUpdateCommand));
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
        questionDeleteService.deleteQuestion(questionDeleteDto);
        return ResponseEntity.noContent().build();
    }
}
