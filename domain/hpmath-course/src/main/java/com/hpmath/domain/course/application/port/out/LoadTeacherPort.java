package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.Teacher;

public interface LoadTeacherPort {
    Teacher loadTeacher(final Long id);
}
