package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.DeleteCourseCommand;

public interface DeleteCourseUseCase {
    void delete(final DeleteCourseCommand deleteCourseCommand);
}
