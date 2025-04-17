package com.hpmath.hpmathcoreapi.course.application.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemoQueryByCourseIdAndDateCommand {
    private final Long courseId;
    private final LocalDate localDate;
}
