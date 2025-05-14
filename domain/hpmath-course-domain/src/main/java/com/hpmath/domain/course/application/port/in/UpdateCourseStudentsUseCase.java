package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.application.dto.UpdateCourseStudentsCommand;

public interface UpdateCourseStudentsUseCase {
    void updateStudents(final UpdateCourseStudentsCommand updateCourseStudentsDto);
}
