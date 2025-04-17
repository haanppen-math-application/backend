package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.UpdateCourseStudentsCommand;

public interface UpdateCourseStudentsUseCase {
    void updateStudents(final UpdateCourseStudentsCommand updateCourseStudentsDto);
}
