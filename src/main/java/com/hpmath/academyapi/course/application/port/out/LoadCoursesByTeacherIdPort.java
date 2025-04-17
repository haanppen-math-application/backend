package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.Course;
import java.util.List;

public interface LoadCoursesByTeacherIdPort {
    List<Course> loadByTeacherId(final Long teacherId);
}
