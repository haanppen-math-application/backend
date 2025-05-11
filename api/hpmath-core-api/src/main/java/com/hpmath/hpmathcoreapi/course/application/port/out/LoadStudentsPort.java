package com.hpmath.hpmathcoreapi.course.application.port.out;

import com.hpmath.hpmathcoreapi.course.domain.Student;
import java.util.List;

public interface LoadStudentsPort {
    List<Student> loadStudents(final List<Long> memberId);
}
