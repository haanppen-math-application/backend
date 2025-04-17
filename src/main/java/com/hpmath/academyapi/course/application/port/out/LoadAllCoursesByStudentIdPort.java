package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.Course;
import java.util.List;

public interface LoadAllCoursesByStudentIdPort {
    List<Course> loadAll(final Long studentId);
}
