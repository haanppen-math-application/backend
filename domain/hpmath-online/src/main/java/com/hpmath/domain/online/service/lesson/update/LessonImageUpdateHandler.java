package com.hpmath.domain.online.service.lesson.update;

import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dto.UpdateOnlineLessonInfoCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LessonImageUpdateHandler implements LessonUpdateHandler {

    @Override
    public void update(OnlineCourse onlineCourse, UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand) {
        if (updateOnlineLessonInfoCommand.imageSrc() == null) {
            return;
        }
        onlineCourse.setImageSrc(updateOnlineLessonInfoCommand.imageSrc());
    }
}
