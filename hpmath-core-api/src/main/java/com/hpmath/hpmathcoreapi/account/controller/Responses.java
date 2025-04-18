package com.hpmath.hpmathcoreapi.account.controller;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.account.dto.Password;
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

    public record MemberInfoResponse(
            Long id,
            String name,
            String phoneNumber,
            Integer grade,
            Role role,
            LocalDateTime registeredDateTime
    ) {
    }

    public record PreviewTeacherResponse(
            Long id,
            String name,
            String phoneNumber,
            LocalDateTime registeredDateTime
    ) {
        public static PreviewTeacherResponse of(final MemberInfoResponse memberInfo) {
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

        public static PreviewStudentResponse of(final MemberInfoResponse memberInfo) {
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
    }
}
