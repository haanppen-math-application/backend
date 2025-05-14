package com.hpmath.hpmathcoreapi.course.domain;

import com.hpmath.client.member.MemberClient.MemberInfo;

public record Teacher(
        Long id,
        String name
) {
    public static Teacher none() {
        return new Teacher(null, "없는 사용자");
    }

    public static Teacher from(MemberInfo memberInfo) {
        return new Teacher(
                memberInfo.memberId(),
                memberInfo.memberName()
        );
    }
}
