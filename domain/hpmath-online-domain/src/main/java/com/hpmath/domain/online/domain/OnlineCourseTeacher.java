package com.hpmath.domain.online.domain;

import lombok.Getter;

@Getter
public class OnlineCourseTeacher {
    private final Long memberId;

    public OnlineCourseTeacher(final Long teacherId) {
        this.memberId = teacherId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
