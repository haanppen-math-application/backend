package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.Course;
import java.util.List;

public interface LoadAllCoursesByStudentIdPort {
    List<Course> loadAll(final Long studentId);
}
