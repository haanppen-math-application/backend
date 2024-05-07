package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.port.in.CourseRegisterUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Memo {
    private Long memoId;
    private Course course;
    private LocalDateTime registerTargetDateTime;
    private String progressed;
    private String homework;

    public static Memo createNewMemo(final LocalDateTime registerTargetDateTime, final String progressed, final String homework) {
        return new Memo(null, null, registerTargetDateTime, progressed, homework);
    }

    public static Memo createByEntity(final Long memoId, final Course course, final LocalDateTime registerTargetDateTime, final String progressed, final String homework) {
        return new Memo(memoId, course, registerTargetDateTime, progressed, homework);
    }
}
