package com.hpmath.app.api.member.controller;

import com.hpmath.app.api.member.controller.Responses.ChangedPasswordResponse;
import com.hpmath.app.api.member.controller.Responses.MyAccountInfoResponse;
import com.hpmath.domain.member.dto.AccountRemoveCommand;
import com.hpmath.domain.member.dto.AccountUpdateCommand;
import com.hpmath.domain.member.dto.ChangedPassword;
import com.hpmath.domain.member.dto.RegisterMemberCommand;
import com.hpmath.domain.member.dto.StudentUpdateCommand;
import com.hpmath.domain.member.dto.UpdateTeacherCommand;
import com.hpmath.domain.member.dto.VerifyAccountCodeCommand;
import com.hpmath.domain.member.service.AccountPasswordRefreshService;
import com.hpmath.domain.member.service.AccountQueryService;
import com.hpmath.domain.member.service.AccountRegisterService;
import com.hpmath.domain.member.service.AccountRemoveService;
import com.hpmath.domain.member.service.AccountUpdateService;
import com.hpmath.common.Role;
import com.hpmath.common.web.authentication.MemberPrincipal;
import com.hpmath.common.web.authenticationV2.Authorization;
import com.hpmath.common.web.authenticationV2.LoginInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountRegisterService accountRegisterService;
    private final AccountUpdateService accountUpdateService;
    private final AccountRemoveService accountRemoveService;
    private final AccountPasswordRefreshService accountPasswordRefreshService;
    private final AccountQueryService queryService;

    @PostMapping("/password/verification")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> authenticateForRefreshPassword(
            @RequestParam(required = true) final String phoneNumber
    ) {
        accountPasswordRefreshService.generateVerificationCode(phoneNumber);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password/verification")
    public ResponseEntity<ChangedPasswordResponse> changePassword(
            @ModelAttribute @Valid final Requests.CheckVerificationCodeRequest request
    ) {
        final ChangedPassword changedPassword = accountPasswordRefreshService.verifyCode(new VerifyAccountCodeCommand(
                request.phoneNumber(), request.verificationCode()));
        return ResponseEntity.ok(new ChangedPasswordResponse(changedPassword.phoneNumber(), changedPassword.changedPassword()));
    }

    @PostMapping
    @Operation(summary = "계정 등록", description = "어플리케이션에 계정을 등록하기 위한 API 입니다 ")
    @ApiResponse(responseCode = "201", description = "계정 생성 성공")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<?> registerMember(
            @Valid @RequestBody final Requests.RegisterMemberRequest registerMemberRequest
    ) {
        final RegisterMemberCommand registerMemberCommand = registerMemberRequest.toCommand();
        accountRegisterService.register(registerMemberCommand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/my", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이름, 전화번호, 비밀번호 수정  API", description = "본인 계정을 수정하기 위한 API 입니다.\n" +
            "필드에 입력된 값이 기존과 같을 경우 || 필드가 null 경우  => 수정되지 않습니다. \n" +
            "다만, 비밀번호의 경우 null이 아니라면 변경작업이 실행됩니다. (이전 비밀번호 검증)")
    @Authorization(opened = true)
    public ResponseEntity<?> updateAccount(
            @LoginInfo final MemberPrincipal info,
            @RequestBody @Valid Requests.AccountUpdateRequest accountUpdateRequest
    ) {
        final AccountUpdateCommand accountUpdateCommand = accountUpdateRequest.toCommand(info.memberId());
        accountUpdateService.updateMember(accountUpdateCommand);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/my")
    public ResponseEntity<MyAccountInfoResponse> getMyAccountInfo(
            @LoginInfo final MemberPrincipal memberPrincipal
    ) {
        return ResponseEntity.ok(MyAccountInfoResponse.of(queryService.getMemberInfo(memberPrincipal.memberId())));
    }

    @DeleteMapping
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "계정 삭제 API", description = "계정을 삭제하기 위한 API 입니다. (원장님, 개발자)만 사용 가능합니다")
    @Authorization(values = Role.ADMIN)
    public ResponseEntity<?> deleteAccounts(
            @RequestBody @Valid Requests.AccountRemoveRequest accountRemoveRequest
    ) {
        final AccountRemoveCommand accountRemoveCommand = accountRemoveRequest.toCommand();
        accountRemoveService.removeAccount(accountRemoveCommand);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/student")
    @Operation(summary = "학생 수정 API", description = "선생님, 원장님이 학생 정보를 수정하는 API")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity<?> modifyStudent(
            @RequestBody @Valid Requests.ModifyStudentRequest modifyStudentRequest
    ) {
        final StudentUpdateCommand studentUpdateCommand = modifyStudentRequest.toCommand();
        accountUpdateService.updateMember(studentUpdateCommand);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/teacher")
    @Operation(summary = "선생 수정 API", description = "원장님만 사용가능한 API")
    @SecurityRequirement(name = "jwtAuth")
    @Authorization(values = Role.ADMIN)
    public ResponseEntity<?> modifyTeacher(
            @RequestBody @Valid Requests.ModifyTeacherRequest modifyTeacherRequest
    ) {
        final UpdateTeacherCommand updateTeacherCommand = modifyTeacherRequest.toCommand();
        accountUpdateService.updateMember(updateTeacherCommand);
        return ResponseEntity.ok(null);
    }
}
