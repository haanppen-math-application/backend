package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.port.out.RegisterCoursePort;
import com.hpmath.domain.course.domain.Student;
import com.hpmath.domain.course.entity.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RegisterCourseAdapter implements RegisterCoursePort {
    private final CourseRepository courseRepository;

    @Override
    public Long register(final com.hpmath.domain.course.domain.Course course) {
        final Course courseEntity = Course.of(course.getCourseName(), course.getTeacher().id(), getStudentIds(course));
        return courseRepository.save(courseEntity).getId();
    }

    private List<Long> getStudentIds(final com.hpmath.domain.course.domain.Course students) {
        return students.getStudents().stream()
                .mapToLong(Student::id)
                .boxed()
                .toList();
    }
}
