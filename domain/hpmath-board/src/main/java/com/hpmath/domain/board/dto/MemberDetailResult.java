package com.hpmath.domain.board.dto;

import com.hpmath.client.member.MemberClient.MemberInfo;

public record MemberDetailResult(
        Long memberId,
        String memberName,
        Integer memberGrade,
        String role
) {
    public static MemberDetailResult from(final MemberInfo memberInfo) {
        return new MemberDetailResult(
                memberInfo.memberId(),
                memberInfo.memberName(),
                memberInfo.memberGrade(),
                memberInfo.role()
        );
    }
}
