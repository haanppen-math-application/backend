package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.*;
import com.hanpyeon.academyapi.account.mapper.RegisterMapper;
import com.hanpyeon.academyapi.account.service.AccountRemoveService;
import com.hanpyeon.academyapi.account.service.AccountUpdateService;
import com.hanpyeon.academyapi.account.service.RegisterService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final RegisterService registerService;
    private final RegisterMapper registerMapper;
    private final AccountUpdateService accountUpdateService;
    private final AccountRemoveService accountRemoveService;

    @PostMapping
    @Operation(summary = "계정 등록", description = "어플리케이션에 계정을 등록하기 위한 API 입니다 ")
    @ApiResponse(responseCode = "201", description = "계정 생성 성공")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> registerMember(@Valid @RequestBody final RegisterRequestDto registerRequestDto) {
        RegisterMemberDto memberDto = registerMapper.createRegisterMemberDto(registerRequestDto);
        registerService.register(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/my", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이름, 전화번호, 비밀번호 수정  API", description = "본인 계정을 수정하기 위한 API 입니다.\n" +
            "필드에 입력된 값이 기존과 같을 경우 || 필드가 null 경우  => 수정되지 않습니다. \n" +
            "다만, 비밀번호의 경우 null이 아니라면 변경작업이 실행됩니다. (이전 비밀번호 검증)")
    public ResponseEntity<?> updateAccount(
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal,
            @Valid AccountUpdateRequest accountUpdateRequest
    ) {

        final Long id = accountUpdateService.updateAccount(
                new AccountUpdateDto(
                        memberPrincipal.memberId(),
                        accountUpdateRequest.phoneNumber(),
                        accountUpdateRequest.name(),
                        accountUpdateRequest.prevPassword(),
                        accountUpdateRequest.newPassword())
        );
        return ResponseEntity.ok(id);
    }

    @DeleteMapping
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "계정 삭제 API", description = "계정을 삭제하기 위한 API 입니다. (원장님, 개발자)만 사용 가능합니다")
    public ResponseEntity<?> deleteStudents(
            @NonNull AccountRemoveRequest accountRemoveRequest
    ) {
        final AccountRemoveDto accountRemoveDto = new AccountRemoveDto(accountRemoveRequest.targetIds());
        accountRemoveService.removeAccount(accountRemoveDto);
        return ResponseEntity.ok(null);
    }
}
