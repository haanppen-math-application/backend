package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.Course;

public interface AddCourseStudentPort {
    void addToCourse(final Course course);
}
