package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.DeleteCourseCommand;

public interface DeleteCourseUseCase {
    void delete(final DeleteCourseCommand deleteCourseCommand);
}
