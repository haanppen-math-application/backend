package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.*;
import com.hanpyeon.academyapi.account.service.AccountPasswordRefreshService;
import com.hanpyeon.academyapi.account.service.AccountRegisterService;
import com.hanpyeon.academyapi.account.service.AccountRemoveService;
import com.hanpyeon.academyapi.account.service.AccountUpdateService;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountRegisterService accountRegisterService;
    private final AccountUpdateService accountUpdateService;
    private final AccountRemoveService accountRemoveService;
    private final AccountPasswordRefreshService accountPasswordRefreshService;

    @PostMapping("/password/verification")
    public ResponseEntity<?> authenticateForRefreshPassword(
            @RequestParam(required = true) String phoneNumber
    ) {
        accountPasswordRefreshService.generateVerificationCode(phoneNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Operation(summary = "계정 등록", description = "어플리케이션에 계정을 등록하기 위한 API 입니다 ")
    @ApiResponse(responseCode = "201", description = "계정 생성 성공")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> registerMember(@Valid @RequestBody final AccountController.RegisterMemberRequest registerMemberRequest) {
        final RegisterMemberCommand registerMemberCommand = registerMemberRequest.toCommand();
        accountRegisterService.register(registerMemberCommand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    record RegisterMemberRequest(
            @NotBlank String name,
            @Schema(description = "학년 정보 ( 0 ~ 11 )", example = "11") @Range(min = 0, max = 11) Integer grade,
            @Schema(description = "전화번호", example = "01000000000") @NotBlank @Pattern(regexp = "^[0-9]+$") String phoneNumber,
            @Schema(description = "등록 유형 ( student, teacher 중 택 1 )", example = "student") @NotNull(message = "teacher / student 둘중 하나여야 합니다") Role role,
            String password
    ) {
        RegisterMemberCommand toCommand() {
            return new RegisterMemberCommand(name(), grade(), phoneNumber(), role(), password());
        }
    }

    @PatchMapping(value = "/my", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이름, 전화번호, 비밀번호 수정  API", description = "본인 계정을 수정하기 위한 API 입니다.\n" +
            "필드에 입력된 값이 기존과 같을 경우 || 필드가 null 경우  => 수정되지 않습니다. \n" +
            "다만, 비밀번호의 경우 null이 아니라면 변경작업이 실행됩니다. (이전 비밀번호 검증)")
    public ResponseEntity<?> updateAccount(
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal,
            @RequestBody @Valid AccountUpdateRequest accountUpdateRequest
    ) {
        final AccountUpdateCommand accountUpdateCommand = accountUpdateRequest.toCommand(memberPrincipal.memberId());
        accountUpdateService.updateAccount(accountUpdateCommand);
        return ResponseEntity.ok().build();
    }

    public record AccountUpdateRequest(
            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            String phoneNumber,
            @NotNull
            String name,
            String prevPassword,
            String newPassword
    ) {
        AccountUpdateCommand toCommand(final Long targetMemberId) {
            return new AccountUpdateCommand(targetMemberId, phoneNumber(), name(), null, prevPassword(), newPassword());
        }
    }

    @GetMapping(value = "/my")
    public ResponseEntity<MyAccountInfo> getMyAccountInfo(
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        return ResponseEntity.ok(accountUpdateService.getMyInfo(memberPrincipal.memberId()));
    }

    @DeleteMapping
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "계정 삭제 API", description = "계정을 삭제하기 위한 API 입니다. (원장님, 개발자)만 사용 가능합니다")
    public ResponseEntity<?> deleteAccounts(
            @RequestBody @NonNull AccountRemoveRequest accountRemoveRequest
    ) {
        final AccountRemoveCommand accountRemoveCommand = accountRemoveRequest.toCommand();
        accountRemoveService.removeAccount(accountRemoveCommand);
        return ResponseEntity.ok(null);
    }

    record AccountRemoveRequest(
            @NonNull List<Long> targetIds
    ) {
        AccountRemoveCommand toCommand() {
            return new AccountRemoveCommand(targetIds());
        }
    }

    @PutMapping("/student")
    @Operation(summary = "학생 수정 API", description = "선생님, 원장님이 학생 정보를 수정하는 API")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> modifyStudent(@RequestBody @Valid ModifyStudentRequest modifyStudentRequest) {
        final AccountUpdateCommand updateCommand = modifyStudentRequest.toCommand();
        accountUpdateService.updateAccount(updateCommand);
        return ResponseEntity.ok(null);
    }

    public record ModifyStudentRequest(
            @NotNull
            Long studentId,
            @NotNull
            @NotBlank
            String name,
            @NotNull
            @NotBlank
            @Pattern(regexp = "^[0-9]+$")
            String phoneNumber,
            @NotNull
            Integer grade
    ) {
        AccountUpdateCommand toCommand() {
            return new AccountUpdateCommand(studentId(), phoneNumber(), name(), grade(), null, null);
        }
    }


    @PutMapping("/teacher")
    @Operation(summary = "선생 수정 API", description = "원장님만 사용가능한 API")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> modifyTeacher(@RequestBody @Valid ModifyTeacherRequest modifyTeacherRequest) {
        final AccountUpdateCommand accountUpdateCommand = modifyTeacherRequest.toCommand();
        accountUpdateService.updateAccount(accountUpdateCommand);
        return ResponseEntity.ok(null);
    }

    public record ModifyTeacherRequest(
            @NotNull
            Long targetId,
            @NotNull
            String name,
            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            String phoneNumber
    ) {
        AccountUpdateCommand toCommand() {
            return new AccountUpdateCommand(targetId(), phoneNumber(),name(), null, null, null);
        }
    }
}
