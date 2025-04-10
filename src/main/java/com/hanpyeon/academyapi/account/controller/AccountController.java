package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.AccountRemoveCommand;
import com.hanpyeon.academyapi.account.dto.AccountUpdateCommand;
import com.hanpyeon.academyapi.account.dto.ChangedPassword;
import com.hanpyeon.academyapi.account.dto.MyAccountInfo;
import com.hanpyeon.academyapi.account.dto.RegisterMemberCommand;
import com.hanpyeon.academyapi.account.dto.StudentUpdateCommand;
import com.hanpyeon.academyapi.account.dto.UpdateTeacherCommand;
import com.hanpyeon.academyapi.account.dto.VerifyAccountCode;
import com.hanpyeon.academyapi.account.service.AccountPasswordRefreshService;
import com.hanpyeon.academyapi.account.service.AccountRegisterService;
import com.hanpyeon.academyapi.account.service.AccountRemoveService;
import com.hanpyeon.academyapi.account.service.AccountUpdateService;
import com.hanpyeon.academyapi.account.service.Password;
import com.hanpyeon.academyapi.account.service.QueryService;
import com.hanpyeon.academyapi.account.validation.GradeConstraint;
import com.hanpyeon.academyapi.account.validation.NameConstraint;
import com.hanpyeon.academyapi.account.validation.PhoneNumberConstraint;
import com.hanpyeon.academyapi.account.validation.RoleConstraint;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountRegisterService accountRegisterService;
    private final AccountUpdateService accountUpdateService;
    private final AccountRemoveService accountRemoveService;
    private final AccountPasswordRefreshService accountPasswordRefreshService;
    private final QueryService queryService;

    @PostMapping("/password/verification")
    public ResponseEntity<?> authenticateForRefreshPassword(
            @RequestParam(required = true) final String phoneNumber
    ) {
        accountPasswordRefreshService.generateVerificationCode(phoneNumber);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password/verification")
    public ResponseEntity<ChangedPasswordResponse> changePassword(
            @ModelAttribute @Valid final CheckVerificationCodeRequest request
    ) {
        final ChangedPassword changedPassword = accountPasswordRefreshService.verifyCode(new VerifyAccountCode(
                request.phoneNumber(), request.verificationCode()));
        return ResponseEntity.ok(new ChangedPasswordResponse(changedPassword.phoneNumber(), changedPassword.changedPassword()));
    }

    record CheckVerificationCodeRequest(
            @PhoneNumberConstraint
            String phoneNumber,
            @NotNull
            String verificationCode
    ) {
    }

    record ChangedPasswordResponse(
            @PhoneNumberConstraint
            String phoneNumber,
            @Valid
            Password changedPassword
    ) {
    }

    @PostMapping
    @Operation(summary = "계정 등록", description = "어플리케이션에 계정을 등록하기 위한 API 입니다 ")
    @ApiResponse(responseCode = "201", description = "계정 생성 성공")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> registerMember(
            @Valid @RequestBody final RegisterMemberRequest registerMemberRequest
    ) {
        final RegisterMemberCommand registerMemberCommand = registerMemberRequest.toCommand();
        accountRegisterService.register(registerMemberCommand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    record RegisterMemberRequest(
            @NameConstraint
            String name,
            @Schema(description = "학년 정보 ( 0 ~ 11 )", example = "11")
            @GradeConstraint
            Integer grade,
            @Schema(description = "전화번호", example = "01000000000")
            @PhoneNumberConstraint
            String phoneNumber,
            @Schema(description = "등록 유형 ( student, teacher 중 택 1 )", example = "student")
            @RoleConstraint
            Role role,
            @Valid
            Password password
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
        accountUpdateService.updateMember(accountUpdateCommand);
        return ResponseEntity.ok().build();
    }

    public record AccountUpdateRequest(
            @PhoneNumberConstraint
            String phoneNumber,
            @NameConstraint
            String name,
            @Valid
            Password prevPassword,
            @Valid
            Password newPassword
    ) {
        AccountUpdateCommand toCommand(final Long targetMemberId) {
            return new AccountUpdateCommand(
                    targetMemberId,
                    phoneNumber(),
                    name(),
                    prevPassword(),
                    newPassword()
            );
        }
    }

    @GetMapping(value = "/my")
    public ResponseEntity<MyAccountInfo> getMyAccountInfo(
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        return ResponseEntity.ok(queryService.getMyInfo(memberPrincipal.memberId()));
    }

    @DeleteMapping
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "계정 삭제 API", description = "계정을 삭제하기 위한 API 입니다. (원장님, 개발자)만 사용 가능합니다")
    public ResponseEntity<?> deleteAccounts(
            @RequestBody @Valid AccountRemoveRequest accountRemoveRequest
    ) {
        final AccountRemoveCommand accountRemoveCommand = accountRemoveRequest.toCommand();
        accountRemoveService.removeAccount(accountRemoveCommand);
        return ResponseEntity.ok(null);
    }

    record AccountRemoveRequest(
            @NonNull
            @Size(min = 1, max = 50)
            List<Long> targetIds
    ) {
        AccountRemoveCommand toCommand() {
            return new AccountRemoveCommand(targetIds());
        }
    }

    @PutMapping("/student")
    @Operation(summary = "학생 수정 API", description = "선생님, 원장님이 학생 정보를 수정하는 API")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> modifyStudent(
            @RequestBody @Valid ModifyStudentRequest modifyStudentRequest
    ) {
        final StudentUpdateCommand studentUpdateCommand = modifyStudentRequest.toCommand();
        accountUpdateService.updateMember(studentUpdateCommand);
        return ResponseEntity.ok(null);
    }

    public record ModifyStudentRequest(
            @NotNull
            Long studentId,
            @NameConstraint
            String name,
            @PhoneNumberConstraint
            String phoneNumber,
            @GradeConstraint
            Integer grade
    ) {
        StudentUpdateCommand toCommand() {
            return new StudentUpdateCommand(
                        studentId(),
                    phoneNumber(),
                    name(),
                    grade()
            );
            }
        }


    @PutMapping("/teacher")
    @Operation(summary = "선생 수정 API", description = "원장님만 사용가능한 API")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> modifyTeacher(
            @RequestBody @Valid ModifyTeacherRequest modifyTeacherRequest
    ) {
        final UpdateTeacherCommand updateTeacherCommand = modifyTeacherRequest.toCommand();
        accountUpdateService.updateMember(updateTeacherCommand);
        return ResponseEntity.ok(null);
    }

    public record ModifyTeacherRequest(
            @NotNull
            Long targetId,
            @NameConstraint
            String name,
            @PhoneNumberConstraint
            String phoneNumber
    ) {
        UpdateTeacherCommand toCommand() {
            return new UpdateTeacherCommand(
                    targetId,
                    name,
                    phoneNumber
            );
        }
    }
}
