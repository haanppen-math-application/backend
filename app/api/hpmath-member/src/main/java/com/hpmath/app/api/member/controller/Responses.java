package com.hpmath.app.api.member.controller;

import com.hpmath.domain.member.dto.MemberInfoResult;
import com.hpmath.domain.member.dto.Password;
import com.hpmath.common.Role;
import java.time.LocalDateTime;

public class Responses {
    public record ChangedPasswordResponse(
            String phoneNumber,
            Password changedPassword
    ) {
    }

    public record JwtResponse(
            String userName,
            String accessToken,
            Role role
    ) {
    }

    public record PreviewTeacherResponse(
            Long id,
            String name,
            String phoneNumber,
            LocalDateTime registeredDateTime
    ) {
        public static PreviewTeacherResponse of(final MemberInfoResult memberInfo) {
            return new PreviewTeacherResponse(
                    memberInfo.id(),
                    memberInfo.name(),
                    memberInfo.phoneNumber(),
                    memberInfo.registeredDateTime()
            );
        }
    }

    public record PreviewStudentResponse(
            Long id,
            String name,
            String phoneNumber,
            Integer grade,
            LocalDateTime registeredDateTime
    ) {

        public static PreviewStudentResponse of(final MemberInfoResult memberInfo) {
            return new PreviewStudentResponse(
                    memberInfo.id(),
                    memberInfo.name(),
                    memberInfo.phoneNumber(),
                    memberInfo.grade(),
                    memberInfo.registeredDateTime()
            );
        }
    }

    public record MyAccountInfoResponse(
            String userName,
            String phoneNumber,
            Role role,
            Integer grade
    ){
        public static MyAccountInfoResponse of(final MemberInfoResult memberInfo) {
            return new MyAccountInfoResponse(
                    memberInfo.name(),
                    memberInfo.phoneNumber(),
                    memberInfo.role(),
                    memberInfo.grade()
            );
        }
    }
}
