package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.CourseUpdateCommand;

public interface UpdateCourseUseCase {
    void updateCourse(final CourseUpdateCommand courseUpdateDto);
}
