package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.port.out.LoadAllCoursesByStudentIdPort;
import com.hpmath.domain.course.domain.Course;
import com.hpmath.domain.course.entity.CourseStudent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LoadAllCoursesByStudentIdAdapter implements LoadAllCoursesByStudentIdPort {
    private final CourseStudentRepository courseStudentRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<Course> loadAll(Long studentId) {
        return courseStudentRepository.findCourseStudentByStudentId(studentId).stream()
                .map(CourseStudent::getCourseEntity)
                .map(courseMapper::mapToCourseDomain)
                .toList();
    }
}
