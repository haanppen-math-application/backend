package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.Teacher;

public interface LoadTeacherPort {
    Teacher loadTeacher(final Long id);
}
