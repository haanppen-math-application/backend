package com.hpmath.hpmathcoreapi.course.domain;

import com.hpmath.client.member.MemberClient.MemberInfo;

public record Student(
        Long id,
        String name,
        Integer grade
) {
    public static Student none() {
        return new Student(null, "없는 사용자", null);
    }

    public static Student from(MemberInfo memberInfo) {
        return new Student(
                memberInfo.memberId(),
                memberInfo.memberName(),
                memberInfo.memberGrade()
        );
    }
}
