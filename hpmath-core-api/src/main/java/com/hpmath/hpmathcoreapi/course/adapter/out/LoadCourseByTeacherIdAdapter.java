package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.application.port.out.LoadCoursesByTeacherIdPort;
import com.hpmath.hpmathcoreapi.course.domain.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class LoadCourseByTeacherIdAdapter implements LoadCoursesByTeacherIdPort {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    @Override
    public List<Course> loadByTeacherId(final Long teacherId) {
        return courseRepository.findAllByTeacherId(teacherId)
                .stream()
                .map(courseMapper::mapToCourseDomain)
                .toList();
    }
}
