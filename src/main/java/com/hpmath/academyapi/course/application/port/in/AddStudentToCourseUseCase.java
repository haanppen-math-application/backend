package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.RegisterStudentCommand;

public interface AddStudentToCourseUseCase {
    void addStudentToCourse(final RegisterStudentCommand registerStudentDto);
}
