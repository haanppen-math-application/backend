package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.Course;

public interface AddCourseStudentPort {
    void addToCourse(final Course course);
}
