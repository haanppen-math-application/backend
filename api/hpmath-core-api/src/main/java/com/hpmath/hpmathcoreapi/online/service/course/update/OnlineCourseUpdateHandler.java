package com.hpmath.hpmathcoreapi.online.service.course.update;

import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dto.OnlineCourseInfoUpdateCommand;

interface OnlineCourseUpdateHandler {
    void update(final OnlineCourse onlineCourse, final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand);
}
