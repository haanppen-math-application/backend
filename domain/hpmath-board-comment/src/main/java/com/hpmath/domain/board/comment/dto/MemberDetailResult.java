package com.hpmath.domain.board.comment.dto;

import com.hpmath.client.member.MemberClient.MemberInfo;
import com.hpmath.common.Role;

public record MemberDetailResult(
        Long memberId,
        String memberName,
        Integer memberGrade,
        Role role
) {
    public static MemberDetailResult from(final MemberInfo memberInfo) {
        if (memberInfo == null) {
            return null;
        }
        return new MemberDetailResult(
        memberInfo.memberId(),
        memberInfo.memberName(),
        memberInfo.memberGrade(),
        memberInfo.role()
        );
    }

    public static MemberDetailResult none(final Long memberId) {
        return new MemberDetailResult(memberId, null, null, null);
    };
}