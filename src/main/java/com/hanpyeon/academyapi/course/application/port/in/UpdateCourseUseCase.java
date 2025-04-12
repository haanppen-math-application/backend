package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.CourseUpdateCommand;

public interface UpdateCourseUseCase {
    void updateCourse(final CourseUpdateCommand courseUpdateDto);
}
