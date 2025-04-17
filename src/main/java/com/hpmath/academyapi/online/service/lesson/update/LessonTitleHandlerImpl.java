package com.hpmath.academyapi.online.service.lesson.update;

import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.domain.OnlineCourseTitle;
import com.hpmath.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.academyapi.online.service.course.OnlineCourseAbstractFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LessonTitleHandlerImpl implements LessonUpdateHandler {
    private final OnlineCourseAbstractFactory onlineCourseAbstractFactory;

    @Override
    public void update(final OnlineCourse onlineCourse, final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        if (Objects.isNull(updateOnlineLessonInfoCommand.title())) {
            return;
        }
        final OnlineCourseTitle title = onlineCourseAbstractFactory.toOnlineCourseTitle(updateOnlineLessonInfoCommand.title());
        onlineCourse.setCourseTitle(title.title());
    }
}
