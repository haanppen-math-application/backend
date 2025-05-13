package com.hpmath.domain.online.service.lesson.update;

import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.domain.OnlineCourseRange;
import com.hpmath.domain.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.domain.online.service.course.OnlineCourseAbstractFactory;
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
