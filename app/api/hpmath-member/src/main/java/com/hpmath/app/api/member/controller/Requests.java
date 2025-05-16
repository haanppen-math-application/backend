package com.hpmath.app.api.member.controller;

import com.hpmath.domain.member.dto.AccountRemoveCommand;
import com.hpmath.domain.member.dto.AccountUpdateCommand;
import com.hpmath.domain.member.dto.Password;
import com.hpmath.domain.member.dto.RegisterMemberCommand;
import com.hpmath.domain.member.dto.StudentUpdateCommand;
import com.hpmath.domain.member.dto.UpdateTeacherCommand;
import com.hpmath.domain.member.validation.GradeConstraint;
import com.hpmath.domain.member.validation.NameConstraint;
import com.hpmath.domain.member.validation.PhoneNumberConstraint;
import com.hpmath.domain.member.validation.RoleConstraint;
import com.hpmath.common.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.lang.NonNull;

class Requests {
    record CheckVerificationCodeRequest(
            @PhoneNumberConstraint
            String phoneNumber,
            @NotNull
            String verificationCode
    ) {
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

    record AccountUpdateRequest(
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

    record AccountRemoveRequest(
            @NonNull
            @Size(min = 1, max = 50)
            List<Long> targetIds
    ) {
        AccountRemoveCommand toCommand() {
            return new AccountRemoveCommand(targetIds());
        }
    }

    record ModifyStudentRequest(
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

    record ModifyTeacherRequest(
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

    @Schema(description = "로그인 정보")
    record LoginRequest(
            @Schema(description = "전화번호", example = "01000000000")
            @PhoneNumberConstraint
            String userPhoneNumber,
            @Valid
            Password password
    ) {
    }
}
