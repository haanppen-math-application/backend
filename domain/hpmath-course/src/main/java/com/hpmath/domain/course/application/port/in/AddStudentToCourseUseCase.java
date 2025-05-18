package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.RegisterStudentCommand;

public interface AddStudentToCourseUseCase {
    void addStudentToCourse(final RegisterStudentCommand registerStudentDto);
}
