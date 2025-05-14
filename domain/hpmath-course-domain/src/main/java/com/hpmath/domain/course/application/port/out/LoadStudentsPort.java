package com.hpmath.domain.course.application.port.out;

import com.hpmath.domain.course.domain.Student;
import java.util.List;

public interface LoadStudentsPort {
    List<Student> loadStudents(final List<Long> memberId);
}
