package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.Teacher;

public interface LoadTeacherPort {
    Teacher loadTeacher(final Long id);
}
