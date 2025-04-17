package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.Course;

public interface UpdateCoursePort {
    void updateCourse(Course course);
}
