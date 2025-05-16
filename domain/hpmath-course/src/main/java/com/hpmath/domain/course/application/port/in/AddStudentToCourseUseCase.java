package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.application.dto.RegisterStudentCommand;

public interface AddStudentToCourseUseCase {
    void addStudentToCourse(final RegisterStudentCommand registerStudentDto);
}
