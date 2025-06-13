package com.hpmath.domain.online.dto;

import com.hpmath.client.member.MemberClient.MemberInfo;

public record OnlineTeacherPreview(
        String teacherName,
        Long teacherId
) {
    public static OnlineTeacherPreview from(final MemberInfo memberInfo) {
        return new OnlineTeacherPreview(
                memberInfo.memberName(),
                memberInfo.memberId()
        );
    }

    public static OnlineTeacherPreview none() {
        return new OnlineTeacherPreview(
                null,
                null
        );
    }
}
