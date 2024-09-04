package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.port.in.CourseRegisterUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Memo {
    private Long memoId;
    private Course course;
    private LocalDate targetDate;
    private String progressed;
    private String homework;

    public static Memo createNewMemo(final LocalDate targetDate, final String progressed, final String homework) {
        return new Memo(null, null, targetDate, progressed, homework);
    }

    public static Memo createByEntity(final Long memoId, final Course course, final LocalDate targetDate, final String progressed, final String homework) {
        return new Memo(memoId, course, targetDate, progressed, homework);
    }
}
