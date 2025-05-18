package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.CourseRegisterCommand;

public interface CourseRegisterUseCase {
    Long register(final CourseRegisterCommand courseRegisterDto);
}
