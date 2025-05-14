package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.application.dto.CourseUpdateCommand;

public interface UpdateCourseUseCase {
    void updateCourse(final CourseUpdateCommand courseUpdateDto);
}
