package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.application.dto.DeleteCourseCommand;

public interface DeleteCourseUseCase {
    void delete(final DeleteCourseCommand deleteCourseCommand);
}
