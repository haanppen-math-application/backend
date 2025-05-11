package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.UpdateCourseStudentsCommand;

public interface UpdateCourseStudentsUseCase {
    void updateStudents(final UpdateCourseStudentsCommand updateCourseStudentsDto);
}
