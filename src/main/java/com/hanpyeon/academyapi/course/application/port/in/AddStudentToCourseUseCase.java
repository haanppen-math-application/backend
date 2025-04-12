package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.RegisterStudentCommand;

public interface AddStudentToCourseUseCase {
    void addStudentToCourse(final RegisterStudentCommand registerStudentDto);
}
