package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.controller.Responses.CourseDetailResponse;

public interface LoadCourseDetailsQuery {
    CourseDetailResponse loadCourseDetails(final Long courseId);
}
