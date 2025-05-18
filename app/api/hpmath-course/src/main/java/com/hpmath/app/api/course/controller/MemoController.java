package com.hpmath.app.api.course.controller;

import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import com.hpmath.domain.course.dto.DeleteMemoCommand;
import com.hpmath.domain.course.dto.ModifyMemoTextCommand;
import com.hpmath.domain.course.service.DeleteMemoService;
import com.hpmath.domain.course.service.MemoRegisterService;
import com.hpmath.domain.course.service.ModifyMemoTextService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemoController {
    private final DeleteMemoService deleteMemoUseCase;
    private final MemoRegisterService memoRegisterUseCase;
    private final ModifyMemoTextService modifyMemoTextUseCase;

    @DeleteMapping("/api/courses/memos/{targetMemoId}")
    @Operation(summary = "메모 삭제 API")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<Void> deleteMemo(
            @Nonnull @PathVariable final Long targetMemoId,
            @Nonnull @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        final DeleteMemoCommand command = new DeleteMemoCommand(memberPrincipal.memberId(), targetMemoId, memberPrincipal.role());
        deleteMemoUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/courses/memos")
    @Operation(summary = "반에 메모를 등록하는 API", description = "메모 등록은 담당 선생님만 가능")
    @Authorization(values = Role.TEACHER)
    public ResponseEntity<Void> addMemo(
            @LoginInfo @NotNull final MemberPrincipal memberPrincipal,
            @RequestBody @Valid final Requests.RegisterMemoRequest registerMemoRequest
    ) {
        memoRegisterUseCase.register(registerMemoRequest.toCommand(memberPrincipal.memberId()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/course/memo")
    @Authorization(values = Role.TEACHER)
    public ResponseEntity<Void> putMemo(
            @RequestBody @Valid Requests.MemoTextModifyRequest memoTextModifyRequest,
            @LoginInfo MemberPrincipal memberPrincipal
    ) {
        final ModifyMemoTextCommand command = memoTextModifyRequest.toCommand(memberPrincipal.memberId());
        modifyMemoTextUseCase.modify(command);
        return ResponseEntity.ok().build();
    }
}
