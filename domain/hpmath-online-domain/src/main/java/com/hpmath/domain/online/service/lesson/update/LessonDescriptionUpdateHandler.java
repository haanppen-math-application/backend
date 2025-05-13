package com.hpmath.domain.online.service.lesson.update;

import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.domain.OnlineLessonDescription;
import com.hpmath.domain.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.domain.online.service.course.OnlineCourseAbstractFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LessonDescriptionUpdateHandler implements LessonUpdateHandler {
    private final OnlineCourseAbstractFactory onlineCourseAbstractFactory;

    @Override
    public void update(OnlineCourse onlineCourse, UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        if (Objects.isNull(updateOnlineLessonInfoCommand.lessonDescribe())) {
            return;
        }
        final OnlineLessonDescription lessonDescription = onlineCourseAbstractFactory.toOnlineLessonDescription(
                updateOnlineLessonInfoCommand.lessonDescribe());
        onlineCourse.setCourseContent(lessonDescription.description());
    }
}
