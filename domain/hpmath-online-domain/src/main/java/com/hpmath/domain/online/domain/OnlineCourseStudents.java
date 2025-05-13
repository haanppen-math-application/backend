package com.hpmath.domain.online.domain;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.List;

public record OnlineCourseStudents(List<OnlineCourseStudent> onlineCourseStudents) {
    private static final int MAX_STUDENTS_COUNT = 100;

    public OnlineCourseStudents {
        validate(onlineCourseStudents);
    }

    private void validate(final List<OnlineCourseStudent> onlineCourseStudents) {
        if (onlineCourseStudents.size() > MAX_STUDENTS_COUNT) {
            throw new BusinessException(ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
    }

    public List<Long> getOnlineCourseStudents() {
        return onlineCourseStudents.stream()
                .map(OnlineCourseStudent::getMemberId)
                .toList();
    }
}
