package com.hpmath.app.api.board.controller;

import com.hpmath.domain.board.dto.QuestionDeleteCommand;
import com.hpmath.domain.board.dto.QuestionRegisterCommand;
import com.hpmath.domain.board.dto.QuestionUpdateCommand;
import com.hpmath.domain.board.service.question.QuestionDeleteService;
import com.hpmath.domain.board.service.question.QuestionRegisterService;
import com.hpmath.domain.board.service.question.QuestionUpdateService;
import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @Authorization(values = Role.STUDENT)
    public ResponseEntity<?> addQuestion(
            @Valid @RequestBody final Requests.QuestionRegisterRequest questionRegisterRequestDto,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final QuestionRegisterCommand questionRegisterCommand = questionRegisterRequestDto.toCommand(memberPrincipal.memberId(), memberPrincipal.role());
        final Long createdQuestionId = questionRegisterService.addQuestion(questionRegisterCommand);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdQuestionId)
                .toUri()
        ).build();
    }

    @Operation(summary = "질문 수정 API", description = "질문 수정은 모두가 가능합니다. ( 학생은 본인이 작성한 질문만 가능 )")
    @SecurityRequirement(name = "jwtAuth")
    @PutMapping
    @Authorization(values = {Role.STUDENT, Role.ADMIN, Role.TEACHER})
    public ResponseEntity<?> updateQuestion(
            @Valid @RequestBody final Requests.QuestionUpdateRequest questionUpdateRequestDto,
            @LoginInfo final MemberPrincipal memberPrincipal
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
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<?> deleteQuestion(
            @ModelAttribute @Valid final Requests.QuestionDeleteRequest questionDeleteRequestDto,
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final QuestionDeleteCommand questionDeleteDto = questionDeleteRequestDto.toCommand(
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        questionDeleteService.deleteQuestion(questionDeleteDto);
        return ResponseEntity.noContent().build();
    }
}
