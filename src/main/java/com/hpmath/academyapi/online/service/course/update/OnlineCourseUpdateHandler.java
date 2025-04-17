package com.hpmath.academyapi.online.service.course.update;

import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.dto.OnlineCourseInfoUpdateCommand;

interface OnlineCourseUpdateHandler {
    void update(final OnlineCourse onlineCourse, final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand);
}
