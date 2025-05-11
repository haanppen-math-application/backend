package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.Course;
import java.util.List;

public interface LoadAllCoursePort {
    List<Course> loadAllCourses();
}
