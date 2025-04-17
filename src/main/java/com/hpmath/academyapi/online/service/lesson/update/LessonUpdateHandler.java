package com.hpmath.academyapi.online.service.lesson.update;

import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.dto.UpdateOnlineLessonInfoCommand;

interface LessonUpdateHandler {
    void update(final OnlineCourse onlineCourse, final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand);
}
