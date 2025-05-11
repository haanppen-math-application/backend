package com.hpmath.hpmathcoreapi.online.service.lesson.update;

import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.domain.OnlineCourseRange;
import com.hpmath.hpmathcoreapi.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.hpmathcoreapi.online.service.course.OnlineCourseAbstractFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LessonRangeUpdateHandler implements LessonUpdateHandler {
    private final OnlineCourseAbstractFactory onlineCourseAbstractFactory;

    @Override
    public void update(final OnlineCourse onlineCourse, final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        if (Objects.isNull(updateOnlineLessonInfoCommand.lessonRange())) {
            return;
        }
        final OnlineCourseRange onlineCourseRange = onlineCourseAbstractFactory.toOnlineCourseRange(
                updateOnlineLessonInfoCommand.lessonRange());
        onlineCourse.setCourseRange(onlineCourseRange.range());
    }
}
