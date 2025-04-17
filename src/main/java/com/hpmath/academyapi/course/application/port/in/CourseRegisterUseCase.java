package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.application.dto.CourseRegisterCommand;

public interface CourseRegisterUseCase {
    Long register(final CourseRegisterCommand courseRegisterDto);
}
