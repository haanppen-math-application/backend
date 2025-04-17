package com.hpmath.hpmathcoreapi.course.controller;

import com.hpmath.hpmathcoreapi.course.application.dto.DeleteMemoCommand;
import com.hpmath.hpmathcoreapi.course.application.dto.ModifyMemoTextCommand;
import com.hpmath.hpmathcoreapi.course.application.port.in.DeleteMemoUseCase;
import com.hpmath.hpmathcoreapi.course.application.port.in.MemoRegisterUseCase;
import com.hpmath.hpmathcoreapi.course.application.port.in.ModifyMemoTextUseCase;
import com.hpmath.hpmathcoreapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemoController {
    private final DeleteMemoUseCase deleteMemoUseCase;
    private final MemoRegisterUseCase memoRegisterUseCase;
    private final ModifyMemoTextUseCase modifyMemoTextUseCase;

    @DeleteMapping("/api/courses/memos/{targetMemoId}")
    @Operation(summary = "메모 삭제 API")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> deleteMemo(
            @Nonnull @PathVariable final Long targetMemoId,
            @Nonnull @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final DeleteMemoCommand command = new DeleteMemoCommand(memberPrincipal.memberId(), targetMemoId);
        deleteMemoUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/courses/memos")
    @Operation(summary = "반에 메모를 등록하는 API", description = "메모 등록은 담당 선생님만 가능")
    public ResponseEntity<?> addMemo(
            @AuthenticationPrincipal @NotNull final MemberPrincipal memberPrincipal,
            @RequestBody @Valid final Requests.RegisterMemoRequest registerMemoRequest
    ) {
        memoRegisterUseCase.register(registerMemoRequest.toCommand(memberPrincipal.memberId()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/course/memo")
    public ResponseEntity<?> putMemo(
            @RequestBody @Valid Requests.MemoTextModifyRequest memoTextModifyRequest,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final ModifyMemoTextCommand command = memoTextModifyRequest.toCommand(memberPrincipal.memberId());
        modifyMemoTextUseCase.modify(command);
        return ResponseEntity.ok().build();
    }
}
