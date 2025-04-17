package com.hpmath.academyapi.online.service.lesson.update;

import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.domain.OnlineLessonDescription;
import com.hpmath.academyapi.online.dto.UpdateOnlineLessonInfoCommand;
import com.hpmath.academyapi.online.service.course.OnlineCourseAbstractFactory;
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
