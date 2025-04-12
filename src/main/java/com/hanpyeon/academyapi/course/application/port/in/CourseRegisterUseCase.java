package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.CourseRegisterCommand;

public interface CourseRegisterUseCase {
    Long register(final CourseRegisterCommand courseRegisterDto);
}
