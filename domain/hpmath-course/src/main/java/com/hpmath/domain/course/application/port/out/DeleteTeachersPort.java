package com.hpmath.domain.course.application.port.out;

import java.util.List;

public interface DeleteTeachersPort {
    void deleteTeachers(List<Long> teacherIds);
}
