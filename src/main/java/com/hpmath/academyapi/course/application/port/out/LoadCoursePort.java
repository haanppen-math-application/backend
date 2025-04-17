package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.Course;

public interface LoadCoursePort {
     Course loadCourse(final Long courseId);
}
