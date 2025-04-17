package com.hpmath.academyapi.course.application.port.out;

import com.hpmath.academyapi.course.domain.Student;
import java.util.List;

public interface LoadStudentsPort {
    List<Student> loadStudents(final List<Long> memberId);
}
