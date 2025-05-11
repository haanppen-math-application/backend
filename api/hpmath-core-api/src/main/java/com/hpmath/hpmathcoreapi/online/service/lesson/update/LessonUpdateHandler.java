package com.hpmath.hpmathcoreapi.online.service.lesson.update;

import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dto.UpdateOnlineLessonInfoCommand;

interface LessonUpdateHandler {
    void update(final OnlineCourse onlineCourse, final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand);
}
