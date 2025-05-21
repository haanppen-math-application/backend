package com.hpmath.domain.board.read.dto;

import com.hpmath.client.member.MemberClient.MemberInfo;
import com.hpmath.common.Role;

public record MemberDetailResult(
        Long memberId,
        String memberName,
        Integer memberGrade,
        Role role
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
