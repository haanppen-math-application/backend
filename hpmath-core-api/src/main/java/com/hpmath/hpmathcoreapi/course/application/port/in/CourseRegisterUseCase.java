package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.application.dto.CourseRegisterCommand;

public interface CourseRegisterUseCase {
    Long register(final CourseRegisterCommand courseRegisterDto);
}
