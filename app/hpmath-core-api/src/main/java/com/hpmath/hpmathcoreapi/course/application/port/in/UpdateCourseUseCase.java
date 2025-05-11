package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.CourseUpdateCommand;

public interface UpdateCourseUseCase {
    void updateCourse(final CourseUpdateCommand courseUpdateDto);
}
