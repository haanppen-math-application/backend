package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.controller.Responses.CourseDetailResponse;

public interface LoadCourseDetailsQuery {
    CourseDetailResponse loadCourseDetails(final Long courseId);
}
