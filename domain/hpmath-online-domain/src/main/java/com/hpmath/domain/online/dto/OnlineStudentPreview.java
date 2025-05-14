package com.hpmath.domain.online.dto;

import com.hpmath.client.member.MemberClient.MemberInfo;

public record OnlineStudentPreview(
        Long studentId,
        String studentName,
        Integer grade
) {
    public static OnlineStudentPreview from(final MemberInfo memberInfo) {
        return new OnlineStudentPreview(
                memberInfo.memberId(),
                memberInfo.memberName(),
                memberInfo.memberGrade()
        );
    }
}
