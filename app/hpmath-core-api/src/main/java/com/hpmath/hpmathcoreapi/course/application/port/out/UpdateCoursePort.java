package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.Course;

public interface UpdateCoursePort {
    void updateCourse(Course course);
}
