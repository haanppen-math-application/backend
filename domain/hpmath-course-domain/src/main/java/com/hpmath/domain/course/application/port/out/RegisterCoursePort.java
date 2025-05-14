package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.Course;

public interface RegisterCoursePort {
    Long register(final Course course);
}
