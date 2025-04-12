package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.UpdateCourseStudentsCommand;

public interface UpdateCourseStudentsUseCase {
    void updateStudents(final UpdateCourseStudentsCommand updateCourseStudentsDto);
}
