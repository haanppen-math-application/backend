package com.hanpyeon.academyapi.course.controller;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import jakarta.annotation.Nonnull;
import java.time.LocalDate;

public class Requests {
    record QueryMemoByCourseIdAndDateRequest(
            @Nonnull Long courseId,
            @Nonnull LocalDate localDate
    ) {
        MemoQueryByCourseIdAndDateCommand createCommand() {
            return new MemoQueryByCourseIdAndDateCommand(courseId, localDate);
        }
    }
}
