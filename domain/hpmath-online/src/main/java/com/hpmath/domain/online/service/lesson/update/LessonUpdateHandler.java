package com.hpmath.domain.online.service.lesson.update;

import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dto.UpdateOnlineLessonInfoCommand;

interface LessonUpdateHandler {
    void update(final OnlineCourse onlineCourse, final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand);
}
