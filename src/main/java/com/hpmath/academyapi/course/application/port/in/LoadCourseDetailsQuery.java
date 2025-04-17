package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.controller.Responses.CourseDetailResponse;

public interface LoadCourseDetailsQuery {
    CourseDetailResponse loadCourseDetails(final Long courseId);
}
