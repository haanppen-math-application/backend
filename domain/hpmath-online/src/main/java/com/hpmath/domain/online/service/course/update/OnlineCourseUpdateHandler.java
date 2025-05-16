package com.hpmath.domain.online.service.course.update;

import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dto.OnlineCourseInfoUpdateCommand;

interface OnlineCourseUpdateHandler {
    void update(final OnlineCourse onlineCourse, final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand);
}
