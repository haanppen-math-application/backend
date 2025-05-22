package com.hpmath.domain.board.read.dto;

import com.hpmath.common.Role;
import com.hpmath.domain.board.read.model.MemberQueryModel;

public record MemberDetailResult(
        Long memberId,
        String memberName,
        Integer memberGrade,
        Role role
) {
    public static MemberDetailResult from(final MemberQueryModel memberQueryModel) {
        return new MemberDetailResult(
                memberQueryModel.getMemberId(),
                memberQueryModel.getMemberName(),
                memberQueryModel.getMemberGrade(),
                memberQueryModel.getRole()
        );
    }
}
