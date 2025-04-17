package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.RegisterStudentCommand;

public interface AddStudentToCourseUseCase {
    void addStudentToCourse(final RegisterStudentCommand registerStudentDto);
}
