package com.hpmath.domain.board.dto;

import com.hpmath.domain.member.Member;

public record MemberDetailResult(
        Long memberId,
        String memberName,
        Integer memberGrade,
        String role
) {
    public static MemberDetailResult from(Member member) {
        return new MemberDetailResult(
                member.getId(),
                member.getName(),
                member.getGrade(),
                member.getRole().getIdentifier()
        );
    }
}
