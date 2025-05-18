package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.Responses.CourseDetailResponse;

public interface LoadCourseDetailsQuery {
    CourseDetailResponse loadCourseDetails(final Long courseId);
}
